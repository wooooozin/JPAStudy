package com.example.jpa.board.repository;

import com.example.jpa.board.entity.BoardComment;
import com.example.jpa.board.entity.BoardLike;
import com.example.jpa.user.entity.AppUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findByUser(AppUser user);
}
