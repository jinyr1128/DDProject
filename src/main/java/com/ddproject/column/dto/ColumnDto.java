package com.ddproject.column.dto;

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

    // getter와 setter
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
