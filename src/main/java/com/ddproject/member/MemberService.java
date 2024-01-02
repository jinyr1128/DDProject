package com.ddproject.member;

import com.ddproject.board.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.member.dto.MemberRequestDto;
import com.ddproject.member.dto.MemberResponseDto;
import com.ddproject.user.domain.User;
import com.ddproject.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final BoardMemberRepository boardMemberRepository;
	private UserRepository userRepository;
	private BoardRepository boardRepository;

	public MemberResponseDto findMember(Long memberId) {
		return toResponseDto(boardMemberRepository
				.findMemberById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다.")));
	}

	public static MemberResponseDto toResponseDto(BoardMember boardMember) {
		return MemberResponseDto.builder()
				.nickname(boardMember.getNickname())
				.role(boardMember.getRole())
				.build();
	}

	public MemberResponseDto updateMember(MemberRequestDto memberRequestDto, Long memberId, User user) throws AccessDeniedException {

		BoardMember boardMember = boardMemberRepository.findMemberById(memberId)
						.orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

		if(!boardMember.getUser().getId().equals(user.getId())) {
			throw new AccessDeniedException("닉네임 수정 권한이 없습니다.");
		}

		if(boardMemberRepository.existsByNicknameAndNotId(memberRequestDto.getMemberName(), boardMember.getId())) {
			throw new IllegalArgumentException("중복된 닉네임 입니다.");
		}

		boardMember.updateNickname(memberRequestDto.getMemberName());
		boardMemberRepository.save(boardMember);

		return toResponseDto(boardMember);
	}

	public void leaveMember(Long memberId, Long boardId) throws AccessDeniedException {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new EntityNotFoundException("해당 보드를 찾을 수 없습니다."));
		BoardMember member = boardMemberRepository.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("해당 멤버를 찾을 수 없습니다."));

		if (!member.getBoard().equals(board)) {
			throw new EntityNotFoundException("보드에 없는 멤버입니다.");
		}

		member.updateStatus(BoardMemberStatus.DELETED);
		boardMemberRepository.save(member);
	}


}
