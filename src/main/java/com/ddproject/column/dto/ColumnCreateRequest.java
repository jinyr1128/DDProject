package com.ddproject.column.dto;

import lombok.Data;

@Data
public class ColumnCreateRequest {
    private String name;
    private String description;
    private Integer sequence;
}
