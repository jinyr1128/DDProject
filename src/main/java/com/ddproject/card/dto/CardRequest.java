package com.ddproject.card.dto;

import lombok.Data;

import java.util.List;

@Data
public class CardRequest {
    private String name;
    private String description;
    private String color;
    private Integer sequence;
    private List<Long> invitedUserIds; // 초대된 사용자 ID 목록
}
