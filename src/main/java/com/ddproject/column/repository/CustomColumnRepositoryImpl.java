package com.ddproject.column.repository;

import com.ddproject.column.entity.Column;
import com.ddproject.column.entity.QColumn;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomColumnRepositoryImpl implements CustomColumnRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomColumnRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
    @Override
    public  List<Column> findColumnsWithSequenceGreaterThanOrEqual(Long boardId, Long columnId, Integer sequence) {
        QColumn qColumn = QColumn.column;
        return queryFactory
                .selectFrom(qColumn)
                .where(qColumn.board.id.eq(boardId)
                        .and(qColumn.id.ne(columnId))
                        .and(qColumn.sequence.goe(sequence)))
                .fetch();
    }

    @Override
    public List<Column> findAllColumnsByBoardIdOrderedBySequence(Long boardId) {
        QColumn qColumn = QColumn.column;
        return queryFactory
                .selectFrom(qColumn)
                .where(qColumn.board.id.eq(boardId))
                .orderBy(qColumn.sequence.asc())
                .fetch();
    }
}
