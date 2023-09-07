package com.example.jpa.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiBoardBookmarkController {

    private final BoardService boardService;

    @PutMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> boardBookmark(
            @PathVariable Long id,
            @RequestHeader("Z-TOKEN") String token
    ) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 올바르지 않습니다.");
        }
        return ResponseResult.result(boardService.addBookmark(id, email));
    }

    @DeleteMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> boardDeleteBookmark(
            @PathVariable Long id,
            @RequestHeader("Z-TOKEN") String token
    ) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 올바르지 않습니다.");
        }
        return ResponseResult.result(boardService.removeBookmar(id, email));
    }

}
