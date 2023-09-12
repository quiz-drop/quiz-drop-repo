package com.sparta.quizdemo.user.service;

import com.sparta.quizdemo.auth.repository.RedisRefreshTokenRepository;
import com.sparta.quizdemo.backoffice.entity.BlackEmail;
import com.sparta.quizdemo.backoffice.repository.BlackEmailRepository;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.service.NotificationService;
import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import com.sparta.quizdemo.user.dto.UserResponseDto;
import com.sparta.quizdemo.user.entity.Address;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.user.repository.AddressRepository;
import com.sparta.quizdemo.user.repository.UserRepository;
import com.sparta.quizdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BlackEmailRepository blackEmailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final NotificationService notificationService;


    public UserService(UserRepository userRepository, AddressRepository addressRepository, BlackEmailRepository blackEmailRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RedisRefreshTokenRepository redisRefreshTokenRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.blackEmailRepository = blackEmailRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
        this.notificationService = notificationService;
    }

    // ADMIN_TOKEN
    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    //회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 ID가 존재합니다.");
        }

        // email 중복확인 to Do
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        List<BlackEmail> blackEmailList = blackEmailRepository.findAll();
        for (BlackEmail blackEmail : blackEmailList) {
            if (blackEmail.getBlackEmail().equals(requestDto.getEmail())) {
                throw new IllegalArgumentException("차단된 Email 입니다.");
            }
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }


        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(requestDto,password, role);
        Address address = new Address(requestDto, user);
        // 사용자 등록
        userRepository.save(user);
        addressRepository.save(address);
    }

    //비밀번호 수정
    @Transactional
    public void updatePassword(UserRequestDto requestDto) {

        User findedUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        if(findedUser.getSocial() != null){
            throw new IllegalArgumentException("소셜 유저는 비밀번호 변경이 불가능합니다. 소셜로그인을 해주세요");
        }
        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());


        findedUser.update(newPassword);

    }

    //회원 정보 수정
    @Transactional
    public void updateUser(UserRequestDto requestDto, User user) {
        User updateUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        Address updateAddress = addressRepository.findByUser_id(user.getId()).orElseThrow(
                () -> new NullPointerException("주소가 존재하지 않습니다.")
        );

        LocalDateTime createdAt = LocalDateTime.now();

        //소셜 유저인 경우
        if(updateUser.getSocial() != null){
            updateUser.update(requestDto);
            updateAddress.update(requestDto);

            notificationService.send(user, NotificationType.USER_UPDATE, NotificationType.USER_UPDATE.getMessage(), createdAt);
        } else {
            if (!passwordEncoder.matches(requestDto.getPassword(), updateUser.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 틀립니다.");
            }

            //새로운 비밀번호 까지 변경
            if(requestDto.getNewPassword()!=null){
                String newPassword = passwordEncoder.encode(requestDto.getNewPassword());

                updateUser.update(requestDto, newPassword);
                updateAddress.update(requestDto);

                notificationService.send(user, NotificationType.USER_UPDATE, NotificationType.USER_UPDATE.getMessage(), createdAt);

            }else{
                //일반 정보 수정
                updateUser.updateUser(requestDto);
                updateAddress.update(requestDto);
                notificationService.send(user, NotificationType.USER_UPDATE, NotificationType.USER_UPDATE.getMessage(), createdAt);
            }
        }
    }


    //회원 탈퇴
    public void deleteUser(UserRequestDto requestDto, User user) {
        User deleteUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );

        if(!passwordEncoder.matches(requestDto.getPassword(),deleteUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        userRepository.delete(deleteUser);
        redisRefreshTokenRepository.deleteRefreshToken(user.getUsername());
    }


    public UserResponseDto getUser(User user) {
        User findUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );

        return new UserResponseDto(findUser);

    }

    public boolean getAddress(User user) {
        User findUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );

        return addressRepository.findByUser_id(findUser.getId()).isPresent(
        );

    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void logout(User user) {
        String username = user.getUsername();
        redisRefreshTokenRepository.deleteRefreshToken(username);
    }

    public void addAddress(UserRequestDto requestDto, User user) {
        User findUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        Address address = new Address(requestDto, user);
        addressRepository.save(address);

    }

    public UserResponseDto getUsername(UserRequestDto requestDto) {
        User findUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        return new UserResponseDto(findUser);

    }


}