package com.ddproject.member.repository;

import com.ddproject.member.entity.BoardMember;
import com.ddproject.member.entity.BoardMemberEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

    Optional<BoardMember> findByUserId(Long userId);

    Optional<BoardMember> findMemberById(Long memberId);

    Optional<BoardMember> findByBoard_Id(Long boardId);

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);

    @Query("SELECT COUNT(m) > 0 FROM BoardMember m WHERE m.nickname = :nickname AND m.id <> :id")
    boolean existsByNicknameAndNotId(String nickname, Long id);

    Optional<BoardMember> findByBoard_IdAndUser_Id(Long boardId, Long userId);

    boolean existsByBoardIdAndUserIdAndRole(Long boardId, Long id, BoardMemberEnum boardMemberEnum);

}