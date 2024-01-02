package com.ddproject.column.service;

import com.ddproject.column.dto.ColumnRequest;
import com.ddproject.column.dto.ColumnResponse;
import com.ddproject.column.entity.Column;
import com.ddproject.column.exception.ColumnErrorCode;
import com.ddproject.column.exception.ColumnException;
import com.ddproject.column.repository.ColumnRepository;
import com.ddproject.board.repository.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.column.repository.CustomColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ColumnService {
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CustomColumnRepository customColumnRepository;


    public ColumnResponse createColumn(ColumnRequest request, Long boardId) {
        Column column = new Column();
        column.setName(request.getName());
        column.setDescription(request.getDescription());
        column.setSequence(request.getSequence());

        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new ColumnException(ColumnErrorCode.BOARD_NOT_FOUND));
        column.setBoard(board);

        Column savedColumn = columnRepository.save(column);
        return convertEntityToResponse(savedColumn);
    }
    public ColumnResponse updateColumnName(Long columnId, String newName) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new ColumnException(ColumnErrorCode.COLUMN_NOT_FOUND));
        column.setName(newName);
        Column updatedColumn = columnRepository.save(column);
        return convertEntityToResponse(updatedColumn);
    }

    public ColumnResponse updateColumnSequence(Long columnId, Integer newSequence) {
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
        return convertEntityToResponse(updatedColumn);
    }

    public void deleteColumn(Long columnId) {
        columnRepository.deleteById(columnId);
    }

    public List<ColumnResponse> getAllColumns(Long boardId) {
        List<Column> columns = customColumnRepository.findAllColumnsByBoardIdOrderedBySequence(boardId);
        return columns.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
    }

    private ColumnResponse convertEntityToResponse(Column column) {
        return new ColumnResponse(column.getId(), column.getName(), column.getDescription(), column.getSequence(), column.getBoard().getId());
    }
}
//JPA버젼
//    public List<ColumnDto> getAllColumns(Long boardId) {
//        List<Column> columns = columnRepository.findAll(); // 실제로는 boardId에 따라 필터링 필요
//        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
//    }


