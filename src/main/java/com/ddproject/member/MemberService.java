package com.ddproject.member;

import com.ddproject.board.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.member.dto.MemberRequestDto;
import com.ddproject.user.domain.User;
import com.ddproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final BoardMemberRepository boardMemberRepository;
	private UserRepository userRepository;
	private BoardRepository boardRepository;

	public void createMember(MemberRequestDto memberRequestDto, User user, Board board) {

		Optional<BoardMember> existingMember = boardMemberRepository.findByBoardAndUser(board, user);
		if (existingMember.isPresent()) {
			throw new IllegalArgumentException("이미 가입한 사용자입니다.");
		} // isDelete 상태에 따른 재가입이 불가능할 수 있는 부분 예외처리 해야함.

		String nickName = memberRequestDto.getMemberName();
		Optional<BoardMember> findMember = boardMemberRepository.findByNickname(nickName);
		if (findMember.isPresent()) {
			throw new IllegalArgumentException("중복된 닉네임 입니다.");
		}

		BoardMember newMember = new BoardMember(board, user, nickName);
		boardMemberRepository.save(newMember);
	}

	public void updateMember(MemberRequestDto memberRequestDto, User user) throws AccessDeniedException {

		Optional<BoardMember> findMember = boardMemberRepository.findByUserId(user.getId());
		if (!findMember.isPresent()) {
			throw new IllegalArgumentException("해당 사용자의 멤버 정보가 없습니다.");
		}

		BoardMember boardMember = findMember.get();
		if (boardMember.getRole() != BoardMemberEnum.ADMIN) {
			throw new AccessDeniedException("수정 권한이 없습니다. 관리자만 수정 가능합니다.");
		}

		String newNickname = memberRequestDto.getMemberName();
		boardMember.updateNickname(newNickname);
		boardMemberRepository.save(boardMember);
	}

//	public  findProfile()

}
