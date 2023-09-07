package com.example.jpa.board.service;

import com.example.jpa.board.entity.BoardBadReport;
import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.*;

import java.util.List;

public interface BoardService {
    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult update(long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllType();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id, boolean isClear);

    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);

    ServiceResult setBoardHits(Long id, String email);

    ServiceResult setBoardLike(Long id, String email);

    ServiceResult setBoardUnLike(Long id, String email);

    ServiceResult addBadReport(Long id, String email, BoardBadReportInput boardBadReportInput);

    List<BoardBadReport> badRepertList();

    ServiceResult scrapBoard(Long id, String email);

    ServiceResult removeScrap(Long id, String email);
}
