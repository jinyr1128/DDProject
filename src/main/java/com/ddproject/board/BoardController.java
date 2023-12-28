package com.ddproject.board;

import com.ddproject.board.BoardDto.BoardRequestDto;
import com.ddproject.board.BoardDto.BoardResponseDto;
import com.ddproject.user.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boars")
public class BoardController {

	private final BoardService boardService;

	@Operation(summary = "보드 생성")
	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto,
														@AuthenticationPrincipal User user) {
		BoardResponseDto responseDto = boardService.createBoard(boardRequestDto, user);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@Operation(summary = "보드 조회")
	@GetMapping("/{boardid}")
	public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id, @AuthenticationPrincipal User user) {
		BoardResponseDto responseDto = boardService.getBoard(id, user);
		return ResponseEntity.ok(responseDto);
	}

	@Operation(summary = "보드 업데이트")
	@PutMapping("/{boardid}")
	public ResponseEntity<Void> updateBoard(@PathVariable Long id,
											@Valid @RequestBody BoardRequestDto boardRequestDto,
											@AuthenticationPrincipal User user) {
		boardService.updateBoard(id, boardRequestDto, user);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "보드 삭제")
	@DeleteMapping("/{boardid}")
	public ResponseEntity<Void> deleteBoard(@PathVariable Long id,
											@AuthenticationPrincipal User user) {
		boardService.deleteBoard(id, user);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "사용자의 모든 보드 조회")
	@GetMapping
	public ResponseEntity<List<BoardResponseDto>> getUserBoards(@AuthenticationPrincipal User user) {
		List<BoardResponseDto> boards = boardService.getUserBoards(user);
		return ResponseEntity.ok(boards);
	}



}
