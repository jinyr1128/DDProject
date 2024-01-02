package com.ddproject.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "닉네임은 영문자와 숫자를 사용한 6글자 이상 10글자 이하로 입력해주세요.")
	private String memberName;



}
