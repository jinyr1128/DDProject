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
	private final MemberService memberService;

	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
		Board board = new Board(boardRequestDto, user);
		board = boardRepository.save(board);
		BoardMember createUserMember = new BoardMember(board, user, user.getUsername());
		boardMemberRepository.save(createUserMember);
		return new BoardResponseDto(board);
	}

	public BoardResponseDto getBoard(Long boardId, UserDetailsImpl userDetails) {
		Board board = boardRepository.findById(boardId)
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
		boardRepository.delete(board);
	}

	public List<BoardResponseDto> getUserBoards(UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		List<Board> boards = boardRepository.findBoardsByUserId(userId);
		return boards.stream()
				.map(BoardResponseDto::new)
				.collect(Collectors.toList());
	}

//	@Transactional
//	public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
//		Board board = boardRepository.findById(boardId)
//				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
//
//		Optional<BoardMember> members = boardMemberRepository.findByBoard_Id(boardId);
//		boolean hasAdminAccess = members.stream()
//				.anyMatch(member -> member.getUser().getId().equals(user.getId()) && member.isAdmin());
//
//		if (!hasAdminAccess) {
//			throw new AccessDeniedException("수정 권한이 없습니다.");
//		}

//	public BoardResponseDto getBoard(Long boardId, User user) {
//		Board board = boardRepository.findById(boardId)
//				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
//
//		boolean isMember = boardMemberRepository.findByBoard_Id(boardId).stream()
//				.anyMatch(member -> member.getUser().getId().equals(user.getId()));
//
//		if (!isMember) {
//			throw new AccessDeniedException("조회 권한이 없습니다.");
//		}
//
//		return new BoardResponseDto(board);
//	}
//
//	@Transactional
//	public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
//		Board board = boardRepository.findById(boardId)
//				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
//
//		Optional<BoardMember> members = boardMemberRepository.findByBoard_Id(boardId);
//		boolean hasAdminAccess = members.stream()
//				.anyMatch(member -> member.getUser().getId().equals(user.getId()) && member.isAdmin());
//
//		if (!hasAdminAccess) {
//			throw new AccessDeniedException("수정 권한이 없습니다.");
//		}
//
//		board.update(boardRequestDto);
//	}
//
//
//	@Transactional
//	public void deleteBoard(Long id, User user) {
//		Board board = boardRepository.findById(id)
//				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
//
//		if (!board.getCreatedBy().equals(user.getId())) {
//			throw new AccessDeniedException("삭제 권한이 없습니다.");
//		}
//
//		boardRepository.delete(board);
//	}
//
//
//	public List<BoardResponseDto> getUserBoards(User user) {
//
//		List<Board> userBoards = boardRepository.findByUserId(user);
//
//		return userBoards.stream()
//				.map(BoardResponseDto::new)
//				.collect(Collectors.toList());
//	}


	public List<Board> findBoardsByUserId(Long userId) {
		Optional<BoardMember> members = boardMemberRepository.findByUserId(userId);
		return members.stream()
				.map(BoardMember::getBoard)
				.distinct() // 중복 제거
				.collect(Collectors.toList());
	}

}
