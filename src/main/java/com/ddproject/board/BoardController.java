package com.ddproject.board;

import com.ddproject.board.dto.BoardRequestDto;
import com.ddproject.board.dto.BoardResponseDto;
import com.ddproject.board.entity.Board;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.member.BoardMember;
import com.ddproject.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ddproject.user.domain.User;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

	private final BoardService boardService;

	@Operation(summary = "보드 생성")
	@PostMapping
	public ResponseEntity<BoardResponseDto> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto,
														@AuthenticationPrincipal UserDetailsImpl userDetails) {
		BoardResponseDto responseDto = boardService.createBoard(boardRequestDto, userDetails.getUser());
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@Operation(summary = "보드 조회")
	@GetMapping("/{boardId}")
	public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId, @AuthenticationPrincipal BoardMember boardMember) {
		BoardResponseDto boardResponseDto = boardService.getBoard(boardId, boardMember);
		return ResponseEntity.ok(boardResponseDto);
	}

	@Operation(summary = "보드 업데이트")
	@PutMapping("/{boardId}")
	public ResponseEntity<Void> updateBoard(@PathVariable Long boardId,
											@Valid @RequestBody BoardRequestDto boardRequestDto,
											@AuthenticationPrincipal BoardMember boardMember) {
		boardService.updateBoard(boardId, boardRequestDto, boardMember);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "보드 삭제")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,
											@AuthenticationPrincipal User user) {
		boardService.deleteBoard(boardId, user);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "사용자의 모든 보드 조회")
	@GetMapping
	public ResponseEntity<List<BoardResponseDto>> getUserBoards(@AuthenticationPrincipal User user) {
		List<BoardResponseDto> boards = boardService.getUserBoards(user);
		return ResponseEntity.ok(boards);
	}



}
