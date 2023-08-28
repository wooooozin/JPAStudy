package com.example.jpa.board.repository;

import com.example.jpa.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardType, Long> {

    BoardType findByBoardName(String title);
}

