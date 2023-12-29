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
	private ZonedDateTime lastModifiedAt;
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "createdBy")
	private User createdBy;

	@OneToMany(mappedBy = "board")
	private Set<BoardMember> invitedUsers;

	@PrePersist
	protected void onCreate() {
		this.createdAt = ZonedDateTime.now();
		this.boardKey = (this.boardKey == null) ? UUID.randomUUID().toString() : this.boardKey;
		this.coverImageColor = (this.coverImageColor == null) ? "white" : this.coverImageColor;
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastModifiedAt = ZonedDateTime.now();
	}

	public Board(BoardRequestDto boardRequestDto, User user) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
		this.createdBy = user;
		this.isDeleted = false;
	}

	public void update(BoardRequestDto boardRequestDto) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
		this.lastModifiedAt = ZonedDateTime.now();
	}

	public void setIsDeleted() {
		this.isDeleted = true;
	}


}
