package com.example.jpa.board.service;

import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.BoardTypeInput;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {
        BoardType boardType = boardRepository.findByBoardName(boardTypeInput.getTitle());
        if (boardType != null && boardTypeInput.getTitle().equals(boardType.getBoardName())) {
            return ServiceResult.fail("이미 동일한 게시판 제목이 존재합니다.");
        }
        BoardType addBoardType = BoardType.builder()
                .boardName(boardTypeInput.getTitle())
                .regDate(LocalDateTime.now())
                .build();
        boardRepository.save(addBoardType);
        return ServiceResult.success();
    }
}
