package com.ddproject.board.entity;

import com.ddproject.board.dto.BoardRequestDto;
import com.ddproject.member.BoardMember;
import com.ddproject.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
 //1
@Entity
@Getter
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String boardKey;
	private String boardTitle;
	private String boardDescription;
	private String coverImageColor;
	private ZonedDateTime createdAt;
	private Long createdBy;
//	private boolean isDeleted;

	@PrePersist
	protected void onCreate() {
		createdAt = ZonedDateTime.now();
		if (boardKey == null) {
			boardKey = UUID.randomUUID().toString();
		}
		if (coverImageColor == null) {
			coverImageColor = "white";
		}
	}

	@OneToMany(mappedBy = "board")
	private Set<BoardMember> invitedUsers;

	public Board(BoardRequestDto boardRequestDto, User user) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
		this.createdBy = user.getId();
	}

	public void update(BoardRequestDto boardRequestDto) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
	}


}