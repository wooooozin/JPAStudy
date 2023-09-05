package com.example.jpa.board.repository;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.entity.BoardHits;
import com.example.jpa.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardHitsRepository extends JpaRepository<BoardHits, Long> {

    long countByBoardAndUser(Board board, AppUser user);
}
