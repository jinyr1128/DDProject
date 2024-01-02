package com.ddproject.column.controller;

import com.ddproject.column.dto.ColumnDto;
import com.ddproject.column.service.ColumnService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class ColumnController {
    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    @Operation(summary = "칼럼 생성")
    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ColumnDto> createColumn(@PathVariable Long boardId, @RequestBody ColumnDto columnDto) {
        columnDto.setBoardId(boardId);
        ColumnDto createdColumn = columnService.createColumn(columnDto);
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    @Operation(summary = "칼럼 업데이트")
    @PatchMapping("/{boardId}/columns/{columnId}/name")
    public ResponseEntity<ColumnDto> updateColumnName(@PathVariable Long columnId, @RequestBody ColumnDto columnDto) {
        ColumnDto updatedColumn = columnService.updateColumnName(columnId, columnDto.getName());
        return ResponseEntity.ok(updatedColumn);
    }

    @Operation(summary = "칼럼 시퀀스 업데이트")
    @PatchMapping("/{boardId}/columns/{columnId}/sequence")
    public ResponseEntity<ColumnDto> updateColumnSequence(@PathVariable Long columnId, @RequestBody ColumnDto columnDto) {
        ColumnDto updatedColumn = columnService.updateColumnSequence(columnId, columnDto.getSequence());
        return ResponseEntity.ok(updatedColumn);
    }

    @Operation(summary = "전체 칼럼 조회")
    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnDto>> getAllColumns(@PathVariable Long boardId) {
        List<ColumnDto> columns = columnService.getAllColumns(boardId);
        return ResponseEntity.ok(columns);
    }

    @Operation(summary = "칼럼 삭제")
    @DeleteMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.ok().build();
    }
}

