package com.ddproject.alarm.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlarmType {
    NEW_CARD_ON_BOARD("new card"),
    NEW_COMMENT_ON_CARD("new comment");
    private final String alarmText;

}
