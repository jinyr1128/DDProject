package com.ddproject.member.entity;

import lombok.Getter;

@Getter
public enum BoardMemberEnum {

	ADMIN(Authority.ADMIN),
	MEMBER(Authority.MEMBER);

	private final String authority;

	BoardMemberEnum(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String MEMBER = "ROLE_MEMBER";
	}

}
