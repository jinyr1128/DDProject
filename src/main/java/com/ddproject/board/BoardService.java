package com.ddproject.board;

import com.ddproject.board.dto.BoardRequestDto;
import com.ddproject.board.dto.BoardResponseDto;
import com.ddproject.board.entity.Board;
import com.ddproject.member.BoardMember;

import com.ddproject.member.BoardMemberRepository;
import com.ddproject.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMemberRepository boardMemberRepository;

	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
		Board board = boardRepository.save(new Board(boardRequestDto, user));
		return new BoardResponseDto(board);
	}

	public BoardResponseDto getBoard(Long boardId, BoardMember boardMember) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		User user = boardMember.getUser();
		boolean isMember = board.getInvitedUsers().stream().anyMatch(member -> member.getUser().equals(user));
		if (!isMember) { throw new AccessDeniedException("조회 권한이 없습니다.");  }
		return new BoardResponseDto(board);
	}

	@Transactional
	public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, BoardMember boardMember) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		boolean hasAdminAccess = board.getInvitedUsers().stream()
				.anyMatch(member -> member.equals(boardMember) && member.isAdmin());
		if (!hasAdminAccess) { throw new AccessDeniedException("수정 권한이 없습니다!"); }
		board.update(boardRequestDto);
	}

	@Transactional
	public void deleteBoard(Long boardId, User user) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		if (!board.getCreatedBy().equals(user)) { throw new AccessDeniedException("삭제 권한이 없습니다."); }
		board.setIsDeleted();
	}

	public List<BoardResponseDto> getUserBoards(User user) {

		List<Board> userBoards = boardRepository.findByUserId(user);

		return userBoards.stream()
				.map(BoardResponseDto::new)
				.collect(Collectors.toList());
	} // Memberreposi를 조회해서 User로 가입된 member의 board를 출력할것인지, UserBoard를 생성해서 따로 출력할것인지 회의.

}
