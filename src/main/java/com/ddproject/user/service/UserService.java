package com.ddproject.user.service;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;

public interface UserService {
    SignupResponseDto signup(SignupUserDto signupUserDto);
    boolean validateSignup(CheckRequestDto checkRequestDto);

    void changePw(PasswordDto passwordDto, String username);

}
