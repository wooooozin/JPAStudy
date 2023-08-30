package com.example.jpa.board.service;

import com.example.jpa.board.model.BoardTypeInput;
import com.example.jpa.board.model.ServiceResult;

public interface BoardService {
    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult update(long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);
}
