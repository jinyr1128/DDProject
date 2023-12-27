package com.ddproject.column.service;

import com.ddproject.column.dto.ColumnDto;
import com.ddproject.column.repository.ColumnRepository;
import com.ddproject.column.entity.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnService {
    private final ColumnRepository columnRepository;

    @Autowired
    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public ColumnDto createColumn(ColumnDto columnDto) {
        Column column = new Column();
        column.setName(columnDto.getName());
        column.setDescription(columnDto.getDescription());
        column.setSequence(columnDto.getSequence());
        // Board 설정 로직 필요

        Column savedColumn = columnRepository.save(column);
        return convertEntityToDto(savedColumn);
    }

    public ColumnDto updateColumnName(Long columnId, String newName) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        column.setName(newName);
        Column updatedColumn = columnRepository.save(column);
        return convertEntityToDto(updatedColumn);
    }

    public ColumnDto updateColumnSequence(Long columnId, Integer newSequence) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        column.setSequence(newSequence);
        Column updatedColumn = columnRepository.save(column);
        return convertEntityToDto(updatedColumn);
    }

    public List<ColumnDto> getAllColumns(Long boardId) {
        List<Column> columns = columnRepository.findAll(); // 실제로는 boardId에 따라 필터링 필요
        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public void deleteColumn(Long columnId) {
        columnRepository.deleteById(columnId);
    }

    private ColumnDto convertEntityToDto(Column column) {
        ColumnDto dto = new ColumnDto();
        dto.setColumnId(column.getId());
        dto.setName(column.getName());
        dto.setDescription(column.getDescription());
        dto.setSequence(column.getSequence());
        dto.setBoardId(column.getBoard().getId());
        return dto;
    }
}
