package com.ddproject.column.controller;

import com.ddproject.column.dto.ColumnRequest;
import com.ddproject.column.dto.ColumnResponse;
import com.ddproject.column.service.ColumnService;
import com.ddproject.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnService columnService;

    @Operation(summary = "칼럼 생성")
    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ApiResponse<ColumnResponse>> createColumn(@PathVariable Long boardId, @RequestBody ColumnRequest request) {
        ColumnResponse createdColumn = columnService.createColumn(request, boardId);
        return ResponseEntity.ok(ApiResponse.success(createdColumn));
    }

    @Operation(summary = "칼럼 업데이트")
    @PatchMapping("/{boardId}/columns/{columnId}/name")
    public ResponseEntity<ApiResponse<ColumnResponse>> updateColumnName(@PathVariable Long columnId, @RequestBody ColumnResponse columnResponse) {
        ColumnResponse updatedColumn = columnService.updateColumnName(columnId, columnResponse.getName());
        return ResponseEntity.ok(ApiResponse.success(updatedColumn));
    }

    @Operation(summary = "칼럼 시퀀스 업데이트")
    @PatchMapping("/{boardId}/columns/{columnId}/sequence")
    public ResponseEntity<ApiResponse<ColumnResponse>> updateColumnSequence(@PathVariable Long columnId, @RequestBody ColumnResponse columnResponse) {
        ColumnResponse updatedColumn = columnService.updateColumnSequence(columnId, columnResponse.getSequence());
        return ResponseEntity.ok(ApiResponse.success(updatedColumn));
    }

    @Operation(summary = "전체 칼럼 조회")
    @GetMapping("/{boardId}/columns")
    public ResponseEntity<ApiResponse<List<ColumnResponse>>> getAllColumns(@PathVariable Long boardId) {
        List<ColumnResponse> columns = columnService.getAllColumns(boardId);
        return ResponseEntity.ok(ApiResponse.success(columns));
    }

    @Operation(summary = "칼럼 삭제")
    @DeleteMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<ApiResponse<Void>> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}

