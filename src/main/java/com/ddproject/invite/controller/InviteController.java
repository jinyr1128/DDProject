package com.ddproject.invite.controller;

import com.ddproject.global.response.Response;
import com.ddproject.invite.dto.InviteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class InviteController {

    // TODO - implement : 경로값으로 받는 유저는 받는 유저이다.

    @PostMapping("/invite/{username}")
    public Response<Void> requestInvite(@PathVariable("username") String username, @AuthenticationPrincipal UserDetails userDetails) {
        InviteDto inviteDTO = InviteDto.builder()
                .sendUsername(userDetails.getUsername())
                .recvUsername(username)
                .boardKey("....")
                .build();
        return Response.success();
    }

}
