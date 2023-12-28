package com.ddproject.card.repository;

import com.ddproject.member.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberRepository2 extends JpaRepository<BoardMember, Long> {
    boolean existsByBoardIdAndUserId(Long boardId, Long userId);
}
