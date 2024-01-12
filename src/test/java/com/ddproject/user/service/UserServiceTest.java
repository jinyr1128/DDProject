package com.ddproject.user.service;

import com.ddproject.user.entity.User;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupUserDto;
import com.ddproject.user.repository.UserRepository;
import com.ddproject.user.validation.SignupValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService sut;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SignupValidator signupValidator;

    @Test
    void 회원가입_성공() {
        String username = "username";
        String email = "useremail";
        String password = "password";
        SignupUserDto signupUserDto = SignupUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        when(userRepository.save(any())).thenReturn(mock(User.class));
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        assertThatCode(() -> sut.signup(signupUserDto)).doesNotThrowAnyException();
    }

    @Test
    void 회원가입_실패() {
        // TODO : implement
    }

    @Test
    void 유효성검증_성공_사용가능한값() {
        CheckRequestDto checkRequestDto = CheckRequestDto.builder()
                .id("testid")
                .type("checkId")
                .build();
        when(signupValidator.validateId(checkRequestDto.getId())).thenReturn(false);
        when(userRepository.existsByUsername("id")).thenReturn(false);

        assertThatCode(() -> sut.validateSignup(checkRequestDto)).doesNotThrowAnyException();

    }
    @Test
    void 유효성검증_실패_사용할수없는값() {
        CheckRequestDto checkRequestDto = CheckRequestDto.builder()
                .id("testid")
                .type("checkId")
                .build();
        when(signupValidator.validateId(checkRequestDto.getId())).thenReturn(true);
        when(userRepository.existsByUsername("id")).thenReturn(true);

        assertThatCode(() -> sut.validateSignup(checkRequestDto)).doesNotThrowAnyException();
    }

    // TODO : implement
    @Test
    void 패스워드변경_성공() {
        PasswordDto passwordDto = PasswordDto.builder()
                .currentPw("currentPw")
                .changePw("changePw")
                .build();
        String username = "username";

    }
}