package com.ddproject.user.service;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupUserDto;

public interface UserService {
    SignupUserDto signup(String username, String email, String password);
    boolean validateSignup(CheckRequestDto checkRequestDto);

    void changePw(PasswordDto passwordDto, String username);

}
