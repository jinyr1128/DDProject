package com.ddproject.board.repository;

import com.ddproject.board.entity.Board;
import com.ddproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findByIdAndIsDeletedFalse(Long boardId);

	List<Board> findByCreatedByAndIsDeletedFalse(User createdBy);
}
