package com.ddproject.board.entity;

import com.ddproject.board.MemberEnum.BoardMemberEnum;
import com.ddproject.board.MemberEnum.BoardMemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Board_Invited_Users")
public class BoardMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	@Enumerated(value = EnumType.STRING)
	private BoardMemberEnum role;

	@Enumerated(EnumType.STRING)
	private BoardMemberStatus status;

	@Column
	private String nickname;


	private ZonedDateTime accountCreationDate;
	private ZonedDateTime lastNicknameUpdate;
//	private String userStatus;

	public BoardMember(User user) {
		this.user = user;
		this.accountCreationDate = ZonedDateTime.now();
	}

	public void updateNickname(String newNickname) {
		this.nickname = newNickname;
		this.lastNicknameUpdate = ZonedDateTime.now();
	}

	public void updateStatus(BoardMemberStatus newStatus) {
		this.status = newStatus;
	}
}
