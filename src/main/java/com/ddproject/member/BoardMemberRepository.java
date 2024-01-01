package com.ddproject.member;

import com.ddproject.board.entity.Board;
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

}