package com.ddproject.member;

import com.ddproject.board.entity.Board;
import com.ddproject.member.dto.MemberRequestDto;
import com.ddproject.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards/{boardId}/member")
public class MemberController {

	private final MemberService memberService;

	// 멤버 생성
	@Operation(summary = "멤버 생성")
	@PostMapping("/createMember")
	public ResponseEntity<String> createMember(@Valid @RequestBody MemberRequestDto memberRequestDto,
											   User user, Board board) {


		memberService.createMember(memberRequestDto, user, board);
		return new ResponseEntity<>("해당 Board에 가입하여 Member 생성에 성공하였습니다.", HttpStatus.CREATED);
	}

	@Operation(summary = "멤버 닉네임 수정")
	@PatchMapping("/memberNickname")
	public ResponseEntity<String> updateMember(@Valid @RequestBody MemberRequestDto memberRequestDto,
											   @AuthenticationPrincipal User user){
		try {
			memberService.updateMember(memberRequestDto, user);
			return ResponseEntity.ok("닉네임이 성공적으로 수정되었습니다.");
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
		}
	}

//	public ResponseEntity<String>

}


// " 이 버튼 하나만 더 만들어주세요 "