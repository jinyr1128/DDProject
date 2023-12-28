package com.ddproject.board;

import com.ddproject.board.entity.Board;
import com.ddproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findBoardByMemberId(Long id);

	List<Board> findByUserId(User user);

}
