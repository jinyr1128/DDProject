package com.ddproject.card.dto;


import lombok.Data;

import java.util.List;

@Data
public class CardDto {
    private Long cardId;
    private String name;
    private String description;
    private String color;
    private Integer sequence;
    private List<Long> invitedUserIds; // 초대된 사용자 ID 목록

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Long> getInvitedUserIds() {
        return invitedUserIds;
    }

    public void setInvitedUserIds(List<Long> invitedUserIds) {
        this.invitedUserIds = invitedUserIds;
    }
}
