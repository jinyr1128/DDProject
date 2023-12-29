package com.ddproject.member;

import com.ddproject.board.entity.Board;
import com.ddproject.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Board_Invited_Users")
public class BoardMember {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id", updatable = false, nullable = false)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	@Enumerated(EnumType.STRING)
	private BoardMemberEnum role = BoardMemberEnum.MEMBER;

	@Enumerated(EnumType.STRING)
	private BoardMemberStatus status = BoardMemberStatus.ACTIVE;

	@Column
	private String nickname;

	@Column
	private ZonedDateTime accountCreationDate;

	@Column
	private ZonedDateTime lastNicknameUpdate;

	@PrePersist
	protected void onCreate() {
		accountCreationDate = ZonedDateTime.now();
	}

	public BoardMember(Board board, User user, String nickname) {
		this.user = user;
		this.board = board;
		this.nickname = nickname;
	}

	public void updateNickname(String newNickname) {
		this.nickname = newNickname;
		this.lastNicknameUpdate = ZonedDateTime.now();
	}

	public void updateStatus(BoardMemberStatus newStatus) {
		this.status = newStatus;
	}

	public boolean isAdmin() {
		return this.role == BoardMemberEnum.ADMIN;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setBoard(Board board) {
		this.board = board;
	}


}
