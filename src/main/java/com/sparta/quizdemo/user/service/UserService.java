package com.sparta.quizdemo.user.service;

import com.sparta.quizdemo.user.dto.UserResponseDto;
import com.sparta.quizdemo.user.entity.Address;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.common.util.JwtUtil;
import com.sparta.quizdemo.user.repository.AddressRepository;
import com.sparta.quizdemo.user.repository.UserRepository;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

//        // email 중복확인 to Do
//        String email = requestDto.getEmail();
//        Optional<User> checkEmail = userRepository.findByEmail(email);
//        if (checkEmail.isPresent()) {
//            throw new IllegalArgumentException("중복된 Email 입니다.");
//        }

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

    //회원 정보 수정
    @Transactional
    public void updateUser(UserRequestDto requestDto, User user) {
        User updateUser = userRepository.findUserById(user.getId()).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );

        Address updateAddress = addressRepository.findByUser_id(user.getId()).orElseThrow(
                () -> new NullPointerException("주소가 존재하지 않습니다.")
        );

        if(updateUser.getSocialId() != null){
            throw new IllegalArgumentException("소셜 로그인한 사용자는 수정이 불가합니다.");
        }

        if(!passwordEncoder.matches(requestDto.getPassword(),updateUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        //String email = requestDto.getEmail();

        updateUser.update(requestDto,newPassword);
        updateAddress.update(requestDto);

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
}