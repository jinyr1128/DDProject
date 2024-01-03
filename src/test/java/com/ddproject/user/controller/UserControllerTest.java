package com.ddproject.user.controller;

import com.ddproject.alarm.service.AlarmService;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;
import com.ddproject.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private AlarmService alarmService;

    @Test
    @WithMockUser
    void 회원가입_성공() throws Exception {
        String username = "username";
        String email = "email@test.com";
        String password = "password";
        SignupUserDto signupUserDto = SignupUserDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        when(userService.signup(signupUserDto)).thenReturn(mock(SignupResponseDto.class));

        mvc.perform(post("/api/v1/users/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(signupUserDto))
        ).andDo(print())
                .andExpect(status().isOk());
    }
}