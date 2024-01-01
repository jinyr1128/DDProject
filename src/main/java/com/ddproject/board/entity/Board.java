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
//	private boolean isDeleted;


	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	@PrePersist
	protected void onCreate() {
		this.createdAt = ZonedDateTime.now();
	}

	@OneToMany(mappedBy = "board")
	private Set<BoardMember> invitedUsers;

	public Board(BoardRequestDto boardRequestDto, User user) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
		this.createdBy = user;
		this.coverImageColor = "white";
		this.boardKey = UUID.randomUUID().toString();
	}

	public void update(BoardRequestDto boardRequestDto) {
		this.boardTitle = boardRequestDto.getBoardTitle();
		this.boardDescription = boardRequestDto.getBoardDescription();
	}


}
