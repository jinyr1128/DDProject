package com.ddproject.board.dto;

import com.ddproject.board.entity.Board;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class BoardResponseDto {

	private Long id;
	private String boardTitle;
	private String boardDescription;
	private ZonedDateTime createdAt;

	public BoardResponseDto(Board board) {
		this.id = board.getId();
		this.boardTitle = board.getBoardTitle();
		this.boardDescription = board.getBoardDescription();
		this.createdAt = board.getCreatedAt();
	}


}
