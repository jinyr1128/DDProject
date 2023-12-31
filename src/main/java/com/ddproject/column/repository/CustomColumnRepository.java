package com.ddproject.column.repository;

import com.ddproject.column.entity.Column;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public interface CustomColumnRepository {
    List<Column> findColumnsWithSequenceGreaterThanOrEqual(Long boardId, Long columnId, Integer sequence);
    List<Column> findAllColumnsByBoardIdOrderedBySequence(Long boardId);
}
