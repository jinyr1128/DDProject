package com.ddproject.invite.service.impl;

import com.ddproject.global.exception.CustomException;
import com.ddproject.global.exception.ErrorCode;
import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.entity.Invite;
import com.ddproject.invite.repository.InviteRepository;
import com.ddproject.invite.service.InviteService;
import com.ddproject.user.domain.User;
import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    @Override
    public void submitInvite(InviteDto inviteDto) {
        Invite invite = Invite.builder()
                .boardKey(inviteDto.getBoardKey())
                .sendUsername(inviteDto.getSendUsername())
                .recvUsername(inviteDto.getRecvUsername())
                .build();
        // TODO : implement - 메소드의 이름에 맞게 알람을 보낼 예정이다.

        inviteRepository.save(invite);
    }

    // TODO : implement - 구체적으로 어떤 기능이 필요할지 생각해볼 필요가 있다.
    @Override
    public InviteDto readInvite(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
           throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        List<Invite> list = inviteRepository.findAll();


        return null;
    }


}
