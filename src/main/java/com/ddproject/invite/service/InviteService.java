package com.ddproject.invite.service;

import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.dto.InviteResponseDto;

import java.util.List;

public interface InviteService {
    void submitInvite(InviteDto inviteDto, String username);
    List<InviteResponseDto> readInvite(String username);
}
