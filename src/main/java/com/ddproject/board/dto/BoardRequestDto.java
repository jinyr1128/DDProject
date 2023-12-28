package com.ddproject.board.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

	@Size(max = 30, message = "제목은 최대 30자까지 작성 가능합니다.")
	private String boardTitle;
	private String boardDescription;
	private String createdBy;

}
