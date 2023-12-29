package com.ddproject.board.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

	@Size(max = 30, message = "제목은 최대 30자까지 작성 가능합니다.")
	private String boardTitle;

	@Size(max = 250, message = "보드 설명은 최대 250자까지 작성 가능합니다.")
	private String boardDescription;

}
