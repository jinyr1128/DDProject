package com.ddproject.card.repository;

import com.ddproject.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT MAX(c.sequence) FROM Card c WHERE c.column.id = :columnId")
    int findMaxSequenceByColumnId(@Param("columnId") Long columnId);

}
