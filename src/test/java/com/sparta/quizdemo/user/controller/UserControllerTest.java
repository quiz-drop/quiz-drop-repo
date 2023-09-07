package com.sparta.quizdemo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.WithMockCustomUser;
import com.sparta.quizdemo.user.dto.SignupRequestDto;
import com.sparta.quizdemo.user.dto.UserRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private static final String BASE_URL = "/api/user";

    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 필터 추가
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void signupTest() throws Exception {
        // given
        String username = "tester1";
        String password = "Test!@12";
        String nickname = "testnick";
        String zipcode = "testzipcode";
        String address1 = "testaddress1";
        String address2 = "testaddress2";
        String email = "test@test.com";
        // when
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setNickname(nickname);
        signupRequestDto.setZip_code(zipcode);
        signupRequestDto.setAddress1(address1);
        signupRequestDto.setAddress2(address2);
        signupRequestDto.setEmail(email);

        String body = objectMapper.writeValueAsString(signupRequestDto);
        //then
        mockMvc.perform(post(BASE_URL + "/signup")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"msg\":\"회원가입 완료\",\"statusCode\":201}"));
    }

    @Test
    @DisplayName("유저 정보 수정 테스트")
    @WithMockCustomUser
    void updateUserTest() throws Exception{
        // given
        String nickname = "testnick";
        String zipcode = "testcode";
        String address1 = "testaddress1";
        String address2 = "testaddress2";
        String password = "Test!@12";
        String newPassword = "Test!@12";
        String email = "test@test.com";
        // when
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNickname(nickname);
        userRequestDto.setZip_code(zipcode);
        userRequestDto.setAddress1(address1);
        userRequestDto.setAddress2(address2);
        userRequestDto.setPassword(password);
        userRequestDto.setNewPassword(newPassword);
        userRequestDto.setEmail(email);

        String body = objectMapper.writeValueAsString(userRequestDto);
        // then
        mockMvc.perform(put(BASE_URL + "/info")
                        .content(body) //HTTP Body에 데이터를 담는다
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"msg\":\"정보 수정 완료\",\"statusCode\":200}"));
    }

    @Test
    void addAddressTest() {
    }

    @Test
    void deleteUserTest() {
    }

    @Test
    void getUserTest() {
    }

    @Test
    void getAddressTest() {
    }

    @Test
    void logoutTest() {
    }
}