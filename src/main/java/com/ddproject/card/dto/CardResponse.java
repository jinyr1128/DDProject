package com.ddproject.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardResponse {
    private Long cardId;
    private String name;
    private String description;
    private String color;
    private Integer sequence;
}

