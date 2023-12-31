package com.ddproject.column.service;

import com.ddproject.column.dto.ColumnDto;
import com.ddproject.column.entity.Column;
import com.ddproject.column.exception.ColumnErrorCode;
import com.ddproject.column.exception.ColumnException;
import com.ddproject.column.repository.ColumnRepository;
import com.ddproject.board.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.column.repository.CustomColumnRepository;
import com.ddproject.column.repository.CustomColumnRepositoryImpl;
import com.ddproject.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ColumnService {
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CustomColumnRepository customColumnRepository;

    public ColumnDto createColumn(ColumnDto columnDto) {
        Column column = new Column();
        column.setName(columnDto.getName());
        column.setDescription(columnDto.getDescription());
        column.setSequence(columnDto.getSequence());

        Board board = boardRepository.findById(columnDto.getBoardId())
                .orElseThrow(() -> new ColumnException(ColumnErrorCode.BOARD_NOT_FOUND));
        column.setBoard(board);

        Column savedColumn = columnRepository.save(column);
        return convertEntityToDto(savedColumn);
    }

    public ColumnDto updateColumnName(Long columnId, String newName) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new ColumnException(ColumnErrorCode.COLUMN_NOT_FOUND));
        column.setName(newName);
        Column updatedColumn = columnRepository.save(column);
        return convertEntityToDto(updatedColumn);
    }

    public ColumnDto updateColumnSequence(Long columnId, Integer newSequence) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new ColumnException(ColumnErrorCode.COLUMN_NOT_FOUND));
        column.setSequence(newSequence);

        List<Column> otherColumns = customColumnRepository.findColumnsWithSequenceGreaterThanOrEqual(
                column.getBoard().getId(), columnId, newSequence
        );

        for (Column otherColumn : otherColumns) {
            otherColumn.setSequence(otherColumn.getSequence() + 1);
            columnRepository.save(otherColumn);
        }

        Column updatedColumn = columnRepository.save(column);
        return convertEntityToDto(updatedColumn);
    }

    public void deleteColumn(Long columnId) {
        columnRepository.deleteById(columnId);
    }

    public List<ColumnDto> getAllColumns(Long boardId) {
        List<Column> columns = customColumnRepository.findAllColumnsByBoardIdOrderedBySequence(boardId);
        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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

//JPA버젼
//    public List<ColumnDto> getAllColumns(Long boardId) {
//        List<Column> columns = columnRepository.findAll(); // 실제로는 boardId에 따라 필터링 필요
//        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
//    }


