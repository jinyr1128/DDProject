package com.ddproject.board;

import com.ddproject.member.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {

	Optional<BoardMember> findByNickname(String nickname);

	List<BoardMember> findByUserId(Long userId);

	List<BoardMember> findByBoard_Id(Long boardId);

}