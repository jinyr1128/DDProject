package com.ddproject.board.dto;

import com.ddproject.board.entity.Board;

import java.time.ZonedDateTime;


public class BoardResponseDto {

	private Long id;
	private String boardTitle;
	private String boardDescription;
	private ZonedDateTime createdAt;
	private ZonedDateTime lastModifiedAt;
	private boolean isDeleted;
	private Long createdById; // createdBy의 ID만 반환

	public BoardResponseDto(Board board) {
		this.id = board.getId();
		this.boardTitle = board.getBoardTitle();
		this.boardDescription = board.getBoardDescription();
		this.createdAt = board.getCreatedAt();
		this.lastModifiedAt = board.getLastModifiedAt();
		this.createdById = board.getCreatedBy().getId();
	}


}
