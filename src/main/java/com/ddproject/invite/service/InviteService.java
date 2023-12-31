package com.ddproject.invite.service;

import com.ddproject.invite.dto.InviteDto;

public interface InviteService {
    void submitInvite(InviteDto inviteDto);
    InviteDto readInvite(String username);
}
