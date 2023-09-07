package com.example.jpa.board.repository;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.entity.BoardHits;
import com.example.jpa.board.entity.BoardLike;
import com.example.jpa.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    long countByBoardAndUser(Board board, AppUser user);
    Optional<BoardLike> findByBoardAndUser(Board board, AppUser user);
}
