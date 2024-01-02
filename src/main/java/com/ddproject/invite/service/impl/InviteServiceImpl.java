package com.ddproject.invite.service.impl;

import com.ddproject.board.entity.Board;
import com.ddproject.board.repository.BoardRepository;
import com.ddproject.global.exception.CustomException;
import com.ddproject.global.exception.ErrorCode;
import com.ddproject.invite.dto.InviteDto;
import com.ddproject.invite.dto.InviteResponseDto;
import com.ddproject.invite.entity.Invite;
import com.ddproject.invite.repository.InviteRepository;
import com.ddproject.invite.service.InviteService;
import com.ddproject.member.dto.MemberRequestDto;
import com.ddproject.member.entity.BoardMember;
import com.ddproject.member.entity.BoardMemberEnum;
import com.ddproject.member.entity.BoardMemberStatus;
import com.ddproject.member.repository.BoardMemberRepository;
import com.ddproject.user.domain.User;
import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    @Override
    public void submitInvite(InviteDto inviteDto) {
        User invitedUser = userRepository.findByUsername(inviteDto.getRecvUsername()).orElseThrow();
        User sendUser = userRepository.findByUsername(inviteDto.getSendUsername()).orElseThrow();

        Long invitedUserId = invitedUser.getId();
        // 권한이 admin인지
        if (boardMemberRepository.findByUserId(sendUser.getId()).orElseThrow().getRole() != BoardMemberEnum.ADMIN) {

        }

        if (!boardMemberRepository.existsByBoardIdAndUserId(inviteDto.getBoardId(), invitedUserId)) {

        }
        Board board = boardRepository.findById(inviteDto.getBoardId()).orElseThrow();

        // 멤버에 초대하기
        BoardMember boardMember = BoardMember.builder()
                .board(board)
                .role(inviteDto.getRole())
                .status(BoardMemberStatus.ACTIVE)
                .nickname(inviteDto.getRecvUsername())
                .user(invitedUser)
                .build();
        boardMemberRepository.save(boardMember);

        //초대 보내기
        Invite invite = Invite.builder()
                .boardId(inviteDto.getBoardId())
                .sendUsername(inviteDto.getSendUsername())
                .recvUsername(inviteDto.getRecvUsername())
                .build();

        inviteRepository.save(invite);
    }

    @Override
    public List<InviteResponseDto> readInvite(String username) {
        //이미 컨트롤러단에 들어올 때 유저의 정보를 알아낼 수 있어서 필요 없을 것 같음.
//        User user = userRepository.findByUsername(username).orElseThrow(() -> {
//            throw new CustomException(ErrorCode.USER_NOT_FOUND);
//        });
        List<Invite> list = inviteRepository.findAllByRecvUsername(username);

        return list.stream().map(item -> modelMapper.map(item, InviteResponseDto.class))
                .collect(Collectors.toList());


    }


}
