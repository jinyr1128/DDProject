package com.ddproject.column.service;

import com.ddproject.board.BoardRepository;
import com.ddproject.board.entity.Board;
import com.ddproject.column.dto.ColumnDto;
import com.ddproject.column.entity.QColumn;
import com.ddproject.column.repository.ColumnRepository;
import com.ddproject.column.entity.Column;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnService {
    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ColumnService(ColumnRepository columnRepository, BoardRepository boardRepository, EntityManager entityManager) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository; // BoardRepository 주입
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    public ColumnDto createColumn(ColumnDto columnDto) {
        Column column = new Column();
        column.setName(columnDto.getName());
        column.setDescription(columnDto.getDescription());
        column.setSequence(columnDto.getSequence());

        Board board = boardRepository.findById(columnDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));
        column.setBoard(board);

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

        // 다른 컬럼들의 순서 업데이트
        updateOtherColumnsSequence(column.getBoard().getId(), columnId, newSequence);

        Column updatedColumn = columnRepository.save(column);
        return convertEntityToDto(updatedColumn);
    }

    private void updateOtherColumnsSequence(Long boardId, Long columnId, Integer newSequence) {
        QColumn qColumn = QColumn.column;
        List<Column> columns = queryFactory
                .selectFrom(qColumn)
                .where(qColumn.board.id.eq(boardId)
                        .and(qColumn.id.ne(columnId))
                        .and(qColumn.sequence.goe(newSequence)))
                .fetch();

        for (Column otherColumn : columns) {
            otherColumn.setSequence(otherColumn.getSequence() + 1);
            columnRepository.save(otherColumn);
        }
    }

//JPA버젼
//    public List<ColumnDto> getAllColumns(Long boardId) {
//        List<Column> columns = columnRepository.findAll(); // 실제로는 boardId에 따라 필터링 필요
//        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
//    }

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
    public List<ColumnDto> getAllColumns(Long boardId) {
        QColumn qColumn = QColumn.column;
        List<Column> columns = queryFactory
                .selectFrom(qColumn)
                .where(qColumn.board.id.eq(boardId))
                .orderBy(qColumn.sequence.asc())
                .fetch();

        return columns.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

}
