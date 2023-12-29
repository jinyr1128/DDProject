package com.ddproject.user.controller;

import com.ddproject.global.response.Response;
import com.ddproject.user.dto.CheckRequestDto;
import com.ddproject.user.dto.PasswordDto;
import com.ddproject.user.dto.SignupResponseDto;
import com.ddproject.user.dto.SignupUserDto;
import com.ddproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Users", description = "유저 API")
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "일반 회원가입 API", description = "일반 회원가입")
    @PostMapping("/signup")
    public Response<SignupResponseDto> signup(@Valid @RequestBody SignupUserDto signupUserDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        SignupResponseDto dto = userService.signup(signupUserDto);

        return Response.success(dto);
    }

    @Operation(summary = "유효성 검증 API", description = "유효성 검증 API")
    @GetMapping(value = "/check")
    public Response<Boolean> validateInfo(CheckRequestDto checkRequestDto) {
        boolean result = userService.validateSignup(checkRequestDto);

        return Response.success(result);
    }
    // TODO : implement

    @Operation(summary = "패스워드 변경 API", description = "패스워드 변경 API")
    @PutMapping(value = "/password" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<Void> changePw(@RequestBody PasswordDto passwordDto, @Parameter(hidden = true)@AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        userService.changePw(passwordDto, userDetails.getUsername());

        return Response.success();
    }
}
