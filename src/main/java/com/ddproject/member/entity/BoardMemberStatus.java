package com.ddproject.member.entity;

import lombok.Getter;

@Getter
public enum BoardMemberStatus {

	DELETED(Status.DELETED),
	DORMANT(Status.DORMANT),
	ACTIVE(Status.ACTIVE);

	private final String status;

	BoardMemberStatus(String status) {
		this.status = status;
	}

	public static class Status {
		public static final String DELETED = "DELETED";
		public static final String DORMANT = "DORMANT";
		public static final String ACTIVE = "ACTIVE";
	}

}
