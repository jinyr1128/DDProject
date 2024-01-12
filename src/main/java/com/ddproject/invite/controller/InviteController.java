package com.ddproject.invite.controller;

import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.ApiResponse;
import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.dto.InviteResponseDto;
import com.ddproject.invite.service.InviteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/invite")
public class InviteController {
    private final InviteService inviteService;

    @Operation(summary = "멤버 초대")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> requestInvite(@RequestBody @Valid InviteDto inviteDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails, BindingResult bindingResult)throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        inviteService.submitInvite(inviteDto, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "유저 자신의 초대 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InviteResponseDto>>> readInvite(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponse.success(inviteService.readInvite(userDetails.getUsername())));

    }

}
