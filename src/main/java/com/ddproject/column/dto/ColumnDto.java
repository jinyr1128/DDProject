package com.ddproject.column.dto;

import lombok.Data;

@Data
public class ColumnDto {
    private Long columnId;
    private String name;
    private String description;
    private Integer sequence;
    private Long boardId;

    // 기본 생성자
    public ColumnDto() {
    }

    // 모든 필드를 포함하는 생성자
    public ColumnDto(Long columnId, String name, String description, Integer sequence, Long boardId) {
        this.columnId = columnId;
        this.name = name;
        this.description = description;
        this.sequence = sequence;
        this.boardId = boardId;
    }
}
