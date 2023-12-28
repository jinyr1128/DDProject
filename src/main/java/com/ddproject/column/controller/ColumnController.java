package com.ddproject.column.controller;

import com.ddproject.column.dto.ColumnDto;
import com.ddproject.column.service.ColumnService;
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

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ColumnDto> createColumn(@PathVariable Long boardId, @RequestBody ColumnDto columnDto) {
        columnDto.setBoardId(boardId);
        ColumnDto createdColumn = columnService.createColumn(columnDto);
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}/columns/{columnId}/name")
    public ResponseEntity<ColumnDto> updateColumnName(@PathVariable Long columnId, @RequestBody ColumnDto columnDto) {
        ColumnDto updatedColumn = columnService.updateColumnName(columnId, columnDto.getName());
        return ResponseEntity.ok(updatedColumn);
    }

    @PatchMapping("/{boardId}/columns/{columnId}/sequence")
    public ResponseEntity<ColumnDto> updateColumnSequence(@PathVariable Long columnId, @RequestBody ColumnDto columnDto) {
        ColumnDto updatedColumn = columnService.updateColumnSequence(columnId, columnDto.getSequence());
        return ResponseEntity.ok(updatedColumn);
    }

    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnDto>> getAllColumns(@PathVariable Long boardId) {
        List<ColumnDto> columns = columnService.getAllColumns(boardId);
        return ResponseEntity.ok(columns);
    }

    @DeleteMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.ok().build();
    }
}

