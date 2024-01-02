package com.ddproject.column.dto;

import lombok.Data;

@Data
public class ColumnRequest {
    private String name;
    private String description;
    private Integer sequence;
}
