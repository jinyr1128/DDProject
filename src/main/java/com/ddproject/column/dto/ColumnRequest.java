package com.ddproject.column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnRequest {
    private String name;
    private String description;
    private Integer sequence;
}
