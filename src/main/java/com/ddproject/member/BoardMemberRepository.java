package com.ddproject.member;

import com.ddproject.board.entity.Board;
import com.ddproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

	Optional<BoardMember> findByNickname(String nickname);

	Optional<BoardMember> findByUserId(Long userId);

	Optional<BoardMember> findByBoardAndUser(Board board, User user);

	Optional<BoardMember> findByBoard_Id(Long boardId);

	boolean existsByBoardIdAndUserId(Long boardId, Long userId);


}