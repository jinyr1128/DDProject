package com.ddproject.column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnResponse {
    private Long columnId;
    private String name;
    private String description;
    private Integer sequence;
    private Long boardId;

}
