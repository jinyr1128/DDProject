package com.ddproject.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String boardKey;
	private String boardName;
	private String boardDescription;
	private String coverImageColor;
//	private boolean isDeleted;

	@OneToMany(mappedBy = "board")
	private Set<BoardMember> invitedUsers;

	public Board(String boardKey, String boardName, String boardDescription, String coverImageColor) {
		this.boardKey = boardKey;
		this.boardName = boardName;
		this.boardDescription = boardDescription;
		this.coverImageColor = coverImageColor;
	}
}
