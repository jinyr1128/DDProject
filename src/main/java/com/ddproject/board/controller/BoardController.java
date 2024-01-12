package com.ddproject.board.controller;

import com.ddproject.board.service.BoardService;
import com.ddproject.board.dto.BoardRequestDto;
import com.ddproject.board.dto.BoardResponseDto;
import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

	private final BoardService boardService;

	@Operation(summary = "보드 생성")
	@PostMapping
	public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@Valid @RequestBody BoardRequestDto boardRequestDto,
																	 @AuthenticationPrincipal UserDetailsImpl userDetails) {
		BoardResponseDto responseDto = boardService.createBoard(boardRequestDto, userDetails.getUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
	}

	@Operation(summary = "보드 조회")
	@GetMapping("/{boardId}")
	public ResponseEntity<ApiResponse<BoardResponseDto>> getBoard(@PathVariable Long boardId,
															   @AuthenticationPrincipal UserDetailsImpl userDetails) {
		BoardResponseDto responseDto = boardService.getBoard(boardId, userDetails);
		return ResponseEntity.ok(ApiResponse.success(responseDto));
	}

	@Operation(summary = "보드 업데이트")
	@PutMapping("/{boardId}")
	public ResponseEntity<ApiResponse<String>> updateBoard(@PathVariable Long boardId,
														@Valid @RequestBody BoardRequestDto boardRequestDto,
														@AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			boardService.updateBoard(boardId, boardRequestDto, userDetails);
			return ResponseEntity.ok(ApiResponse.success("보드 정보가 성공적으로 업데이트되었습니다."));
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("403", "수정 권한이 없습니다."));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("404", "해당 보드를 찾을 수 없습니다."));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(ApiResponse.error("400", e.getMessage()));
		}
	}

	@Operation(summary = "보드 삭제")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long boardId,
														@AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			boardService.deleteBoard(boardId, userDetails);
			return ResponseEntity.ok(ApiResponse.success("보드가 성공적으로 삭제되었습니다."));
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponse.error("403", "삭제 권한이 없습니다."));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(ApiResponse.error("404", "해당 보드를 찾을 수 없습니다."));
		}
	}

	@Operation(summary = "유저가 가입한 모든 멤버 조회")
	@GetMapping("/userBoards")
	public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getUserBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<BoardResponseDto> boards = boardService.getUserBoards(userDetails);
		return ResponseEntity.ok(ApiResponse.success(boards));
	}

}
