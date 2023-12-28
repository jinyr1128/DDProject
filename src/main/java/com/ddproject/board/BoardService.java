package com.ddproject.board;

import com.ddproject.board.BoardDto.BoardRequestDto;
import com.ddproject.board.BoardDto.BoardResponseDto;
import com.ddproject.board.entity.Board;
import com.ddproject.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
		Board board = boardRepository.save(new Board(boardRequestDto, user));
		return new BoardResponseDto(board);
	}

	public BoardResponseDto getMemberBoard(Long id) {
		Board board = boardRepository.findBoardByMemberId(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		return new BoardResponseDto(board);
	}

	@Transactional
	public void updateBoard(Long id, BoardRequestDto boardRequestDto, User user) {
		Board board = boardRepository.findById(id)
				.filter(b -> b.getUser().equals(user))
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		board.update(boardRequestDto);
	}

	@Transactional
	public void deleteBoard(Long id, User user) {
		Board board = boardRepository.findById(id)
				.filter(b -> b.getUser().equals(user))
				.orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));
		boardRepository.delete(board);
	}


	public List<BoardResponseDto> getUserBoards(User user) {

		List<Board> userBoards = boardRepository.findByUserId(user);

		return userBoards.stream()
				.map(BoardResponseDto::new)
				.collect(Collectors.toList());
	}

}
