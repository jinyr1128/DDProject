package com.ddproject.user.service;
import com.ddproject.alarm.dto.AlarmDto;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    SignupResponseDto signup(SignupUserDto signupUserDto);
    boolean validateSignup(CheckRequestDto checkRequestDto);

    void changePw(PasswordDto passwordDto, String username);
    Page<AlarmDto> alarmList(String username, Pageable pageable);

}
