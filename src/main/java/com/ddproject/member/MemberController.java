package com.ddproject.member;

import com.ddproject.common.security.UserDetailsImpl;
import com.ddproject.global.response.Response;
import com.ddproject.member.dto.MemberRequestDto;
import com.ddproject.member.dto.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards/{boardId}/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "멤버 닉네임 수정")
	@PatchMapping("/nickName/{memberId}")
	public ResponseEntity<Response<String>> updateMeber(@PathVariable Long memberId,
														@Valid @RequestBody MemberRequestDto memberRequestDto,
														@AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			MemberResponseDto memberResponseDto = memberService.updateMember(memberRequestDto, memberId, userDetails.getUser());
			return ResponseEntity.ok(Response.success("닉네임이 성공적으로 수정되었습니다. 새 닉네임: " + memberResponseDto.getNickname()));
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error("401", "수정 권한이 없습니다."));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("404", "해당 멤버를 찾을 수 없습니다."));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Response.error("400", e.getMessage()));
		}
	}

	@GetMapping
	@Operation(summary = "멤버 닉네임 조회")
	public ResponseEntity<Response<String>> findMember(@PathVariable Long memberId){
		MemberResponseDto memberResponseDto = memberService.findMember(memberId);
		return ResponseEntity.ok(Response.success(memberResponseDto.getNickname()));
	}

	@Operation (summary = "멤버 탈퇴하기")
	@DeleteMapping("/leave/{memberId}")
	public ResponseEntity<Response<String>> leaveMember(@PathVariable Long boardId,
														@PathVariable Long memberId) {
		try {
			memberService.leaveMember(memberId, boardId);
			return ResponseEntity.ok(Response.success("성공적으로 탈퇴했습니다."));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Response.error("400", e.getMessage()));
		}
	}
}
