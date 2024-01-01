package com.ddproject.board;

import com.ddproject.board.dto.BoardRequestDto;
import com.ddproject.board.dto.BoardResponseDto;
import com.ddproject.board.entity.Board;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.member.BoardMember;

import com.ddproject.member.BoardMemberEnum;
import com.ddproject.member.BoardMemberRepository;
import com.ddproject.member.MemberService;
import com.ddproject.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMemberRepository boardMemberRepository;

	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
		Board board = new Board(boardRequestDto, user);
		board = boardRepository.save(board);
		BoardMember createUserMember = new BoardMember(board, user, user.getUsername());
		boardMemberRepository.save(createUserMember);
		return new BoardResponseDto(board);
	}

	public BoardResponseDto getBoard(Long boardId, UserDetailsImpl userDetails) {
		Board board = (Board) boardRepository.findByIdAndIsDeletedFalse(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

		boolean isMember = boardMemberRepository.findByBoard_IdAndUser_Id(boardId, userDetails.getUser().getId())
				.isPresent();

		if (!isMember) {
			throw new AccessDeniedException("조회 권한이 없습니다.");
		}

		return new BoardResponseDto(board);
	}


	@Transactional
	public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, UserDetailsImpl userDetails) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

		boolean hasUpdateAccess = board
				.getCreatedBy()
				.getId()
				.equals(userDetails.getUser().getId()) ||
				boardMemberRepository.existsByBoardIdAndUserIdAndRole(boardId,
						userDetails.getUser().getId(), BoardMemberEnum.ADMIN);

		if (!hasUpdateAccess) { throw new AccessDeniedException("수정 권한이 없습니다."); }
		board.update(boardRequestDto);
	}

	@Transactional
	public void deleteBoard(Long boardId, UserDetailsImpl userDetails) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new EntityNotFoundException("해당 보드를 찾을 수 없습니다."));

		if (!board.getCreatedBy().equals(userDetails.getUser())) {
			throw new AccessDeniedException("삭제 권한이 없습니다.");
		}
		board.delete();
	}

	public List<BoardResponseDto> getUserBoards(UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		List<Board> boards = boardRepository.findByCreatedByAndIsDeletedFalse(user);
		return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}


}
