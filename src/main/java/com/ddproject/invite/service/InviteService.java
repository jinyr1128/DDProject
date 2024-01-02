package com.ddproject.invite.service;

import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.dto.InviteResponseDto;
import com.ddproject.invite.entity.Invite;

import java.util.List;

public interface InviteService {
    void submitInvite(InviteDto inviteDto);
    List<InviteResponseDto> readInvite(String username);
}
