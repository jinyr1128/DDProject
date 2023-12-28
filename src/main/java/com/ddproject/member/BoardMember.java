package com.ddproject.member;

import com.ddproject.board.entity.Board;
import com.ddproject.user.domain.User;
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

	@PrePersist
	protected void onCreate() {
		accountCreationDate = ZonedDateTime.now();
	}

	public BoardMember(User user) {
		this.user = user;
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

	public enum BoardMemberStatus {
	}

	@Getter
	public enum BoardMemberEnum {

		ADMIN(Authority.ADMIN),
		MEMBER(Authority.MEMBER);

		private final String authority;

		BoardMemberEnum(String authority) {
			this.authority = authority;
		}

		public String getAuthority() {
			return this.authority;
		}

		public static class Authority {
			public static final String ADMIN = "ROLE_ADMIN";
			public static final String MEMBER = "ROLE_MEMBER";
		}

	}
}
