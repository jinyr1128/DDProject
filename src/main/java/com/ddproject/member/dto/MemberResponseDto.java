package com.ddproject.member.dto;

import com.ddproject.member.BoardMemberEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MemberResponseDto {

	private String nickname;
	private BoardMemberEnum role;

}
