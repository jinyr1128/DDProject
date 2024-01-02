package com.ddproject.invite.controller;

import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.Response;
import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.dto.InviteResponseDto;
import com.ddproject.invite.service.InviteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Response<Void>> requestInvite(@RequestBody @Valid InviteDto inviteDto, @PathVariable String username,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(userDetails);
        InviteDto inviteDTO = InviteDto.builder()
                .sendUsername(userDetails.getUsername())
                .recvUsername(username)
                .boardId(inviteDto.getBoardId()) // boardKey 대신 boardId를 사용
                .build();

        inviteService.submitInvite(inviteDTO);
        return ResponseEntity.ok(Response.success());
    }

    @Operation
    @GetMapping
    public ResponseEntity<Response<List<InviteResponseDto>>> readInvite(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(Response.success(inviteService.readInvite(userDetails.getUsername())));

    }

}
