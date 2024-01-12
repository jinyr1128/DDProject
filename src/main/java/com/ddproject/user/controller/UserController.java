package com.ddproject.user.controller;

import com.ddproject.alarm.dto.AlarmDto;
import com.ddproject.alarm.service.AlarmService;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.ApiResponse;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;
import com.ddproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Users", description = "유저 API")
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AlarmService alarmService;

    @Operation(summary = "일반 회원가입 API", description = "일반 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupUserDto signupUserDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        SignupResponseDto dto = userService.signup(signupUserDto);

        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @Operation(summary = "유효성 검증 API", description = "유효성 검증 API")
    @GetMapping(value = "/check")
    public ResponseEntity<ApiResponse<Boolean>> validateEmail(CheckRequestDto checkRequestDto) {
        boolean result = userService.validateSignup(checkRequestDto);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "패스워드 변경 API", description = "패스워드 변경 API")
    @PutMapping(value = "/password")
    public ResponseEntity<ApiResponse<Void>> changePw(@Valid @RequestBody PasswordDto passwordDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        log.info(userDetails);
        userService.changePw(passwordDto, userDetails.getUsername());

        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "알람 API", description = "알람 API")
    @GetMapping("/alarm")
    public ResponseEntity<ApiResponse<Page<AlarmDto>>> alarm(Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(ApiResponse.success(userService.alarmList(userDetails.getUsername(), pageable)));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails userDetails) {
        return alarmService.connectAlarm(userDetails.getUsername());
    }
}
