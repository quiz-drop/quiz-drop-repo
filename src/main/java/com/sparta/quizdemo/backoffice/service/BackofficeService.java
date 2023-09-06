package com.sparta.quizdemo.backoffice.service;

import com.sparta.quizdemo.backoffice.entity.Visitor;
import com.sparta.quizdemo.backoffice.repository.BackofficeRepository;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import com.sparta.quizdemo.user.dto.UserResponseDto;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BackofficeService implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final BackofficeRepository backofficeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    public ResponseEntity<List<Visitor>> getVisitors() {
        List<Visitor> visitorList = backofficeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(visitorList);
    }

    public ResponseEntity<List<Visitor>> findVisitor(String keyword) {
        List<Visitor> visitorList = backofficeRepository.findAll();
        List<Visitor> findingList = new ArrayList<>();

        if (keyword.isBlank()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        } else {
            for (Visitor visitor : visitorList) {
                if (visitor.getVisitorIP().contains(keyword)) {
                    findingList.add(visitor);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(findingList);
    }

    public Integer countVisitor() {
        List<Visitor> visitorList = backofficeRepository.findAll();
        return visitorList.size();
    }

    public Long countOrder() {
        List<User> userList = userRepository.findAll();
        Long totalOrderCount = 0L;
        for (User user : userList) {
            totalOrderCount += user.getOrderCount();
        }
        return totalOrderCount;
    }

    public ResponseEntity<List<UserResponseDto>> getUserList() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            userResponseDtoList.add(new UserResponseDto(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    public ResponseEntity<List<UserResponseDto>> findOneUser(String keyword) {
        List<User> userList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        if (keyword.isBlank()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        } else {
            for (User user : userList) {
                if (user.getUsername().contains(keyword)) {
                    userResponseDtoList.add(new UserResponseDto(user));
                }

                if (user.getNickname().contains(keyword)) {
                    userResponseDtoList.add(new UserResponseDto(user));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDtoList);
    }

    public ResponseEntity<UserResponseDto> updateOneUSer(String userName, UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new NullPointerException("해당 ID의 유저가 존재하지 않습니다."));

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("관리자 권한을 가진 유저입니다.");
        } else {
            String newPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
            user.update(userRequestDto, newPassword);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDto(user));
        }
    }

    public ResponseEntity<ApiResponseDto> deleteOneUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new NullPointerException("해당 ID의 유저가 존재하지 않습니다."));

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new IllegalArgumentException("관리자 권한을 가진 유저입니다.");
        } else {
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("해당 ID의 유저를 탈퇴시켰습니다.", HttpStatus.OK.value()));
        }
    }

    // 방문자 정보를 가져오기 위한 인터셉터 설정
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String visitorIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String today = LocalDate.now().toString();
        String key = visitorIP + "_" + today;

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        if (Boolean.FALSE.equals(valueOperations.getOperations().hasKey(key))) {
            valueOperations.set(key, userAgent);
        }

        return true;
    }

    // nginx 나 프록시를 사용하는 방문자의 경우 ip 주소를 127.0.0.1 로 가져오는 것을 방지
    public String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    @Scheduled(cron = "0 */5 * * * *") // 5분마다 레디스에 쌓인 방문자들 정보를 DB로 전송
    public void updateVisitorData() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String visitorIP = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!backofficeRepository.existsByVisitorIPAndDate(visitorIP, date)){
                Visitor visitor = Visitor.builder()
                        .userAgent(userAgent)
                        .visitorIP(visitorIP)
                        .date(date)
                        .build();

                backofficeRepository.save(visitor);
            }

            redisTemplate.delete(key);
        }
    }
}
