package com.example.jpa.board.controller;

import com.example.jpa.board.entity.BoardBadReport;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminBoardController {

    private final BoardService boardService;

    @GetMapping("/api/admin/board/badreport")
    public ResponseEntity<?> badReport() {
        List<BoardBadReport> boardBadReports = boardService.badRepertList();
        return ResponseResult.success(boardBadReports);
    }

}
