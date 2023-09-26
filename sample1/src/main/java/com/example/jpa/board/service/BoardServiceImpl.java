package com.example.jpa.board.service;

import com.example.jpa.board.entity.*;
import com.example.jpa.board.model.*;
import com.example.jpa.board.repository.*;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;
    private final BoardTypeCustomRepository boardTypeCustomRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardBadReportRepository boardBadReportRepository;
    private final BoardScrapRepository boardScrapRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardCommentRepository boardCommentRepository;

    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardTypeInput.getTitle());
        if (boardType != null && boardTypeInput.getTitle().equals(boardType.getBoardName())) {
            return ServiceResult.fail("이미 동일한 게시판 제목이 존재합니다.");
        }
        BoardType addBoardType = BoardType.builder()
                .boardName(boardTypeInput.getTitle())
                .regDate(LocalDateTime.now())
                .usingYn(true)
                .build();
        boardTypeRepository.save(addBoardType);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(long id, BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (!optionalBoardType.isPresent()) {
            return ServiceResult.fail("수정할 게시판 타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();
        if (boardTypeInput.getTitle().equals(boardType.getBoardName())) {
            return ServiceResult.fail("동일한 게시판 제목이 존재합니다.");
        }

        boardType.setBoardName(boardTypeInput.getTitle());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteBoard(Long id) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (!optionalBoardType.isPresent()) {
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();

        if (boardRepository.countByBoardType(boardType) > 0) {
            return ServiceResult.fail("삭제할 게시판 타입에 게시글이 존재합니다.");
        }

        boardTypeRepository.delete(boardType);

        return ServiceResult.success();
    }

    @Override
    public List<BoardType> getAllType() {

        return boardTypeRepository.findAll();
    }

    @Override
    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (!optionalBoardType.isPresent()) {
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();
        boardType.setUsingYn(boardTypeUsing.isUsingYn());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);
        return ServiceResult.success();
    }

    @Override
    public List<BoardTypeCount> getBoardTypeCount() {

        return boardTypeCustomRepository.getBoardTypeCount();
    }

    @Override
    public ServiceResult setBoardTop(Long id, boolean isClear) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        if (board.isTopYn() == isClear) {
            if (isClear) {
                return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
            } else {
                return ServiceResult.fail("이미 게시글이 최상단에 해제되어 있습니다. ");
            }
        }

        board.setTopYn(isClear);
        boardRepository.save(board);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        board.setPublishStartDate(boardPeriod.getStartDate());
        board.setPublishEndDate(boardPeriod.getEndDate());
        boardRepository.save(board);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardHits(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        if (boardHitsRepository.countByBoardAndUser(board, user) > 0) {
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }
        boardHitsRepository.save(BoardHits.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardLike(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();
        long boardLikeCount = boardLikeRepository.countByBoardAndUser(board, user);
        if (boardLikeCount > 0) {
            return ServiceResult.fail("이미 좋아요한 게시글 입니다.");
        }
        boardLikeRepository.save(BoardLike.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardUnLike(Long id, String email) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        Optional<BoardLike> optionalBoardLike = boardLikeRepository.findByBoardAndUser(board, user);
        if (!optionalBoardLike.isPresent()) {
            return ServiceResult.fail("좋아요한 내용이 없습니다.");
        }
        BoardLike boardLike = optionalBoardLike.get();
        boardLikeRepository.delete(boardLike);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult addBadReport(
            Long id,
            String email,
            BoardBadReportInput boardBadReportInput
    ) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        BoardBadReport boardBadReport = BoardBadReport.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getEmail())

                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .bardUserId(board.getUser().getId())
                .boardContents(board.getContents())
                .boardRegTime(board.getRegDate())
                .regDate(LocalDateTime.now())
                .comments(boardBadReportInput.getComments())
                .build();
        boardBadReportRepository.save(boardBadReport);

        return ServiceResult.success();
    }

    @Override
    public List<BoardBadReport> badRepertList() {
        return boardBadReportRepository.findAll();
    }

    @Override
    public ServiceResult scrapBoard(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        BoardScrap boardScrap = BoardScrap.builder()
                .user(user)

                .boardId(board.getId())
                .boardTypeId(board.getBoardType().getId())
                .boardTitle(board.getTitle())
                .boardRegTime(board.getRegDate())
                .boardUserId(board.getUser().getId())
                .boardContents(board.getContents())

                .regDate(LocalDateTime.now())
                .build();

        boardScrapRepository.save(boardScrap);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeScrap(Long id, String email) {
        Optional<BoardScrap> optionalBoardScrap = boardScrapRepository.findById(id);
        if (!optionalBoardScrap.isPresent()) {
            return ServiceResult.fail("스크랩한 게시글이 존재하지 않습니다.");
        }
        BoardScrap boardScrap = optionalBoardScrap.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        if (user.getId() != boardScrap.getUser().getId()) {
            return ServiceResult.fail("회원 정보가 일치하지 않습니다.");
        }

        boardScrapRepository.delete(boardScrap);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult addBookmark(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();
        BoardBookmark boardBookmark = BoardBookmark.builder()
                .user(user)
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardTypeId(board.getBoardType().getId())
                .boardUrl(getBoardUrl(board.getId()))
                .regDate(LocalDateTime.now())
                .build();
        boardBookmarkRepository.save(boardBookmark);
        return ServiceResult.success();
    }

    private String getBoardUrl(long boardId) {
        return String.format("/board/%d", boardId);
    }

    @Override
    public ServiceResult removeBookmar(Long id, String email) {
        Optional<BoardBookmark> optionalBoardBookmark = boardBookmarkRepository.findById(id);
        if (!optionalBoardBookmark.isPresent()) {
            return ServiceResult.fail("삭제할 북마크가 없습니다.");
        }
        BoardBookmark boardBookmark = optionalBoardBookmark.get();

        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        if (user.getId() != boardBookmark.getUser().getId()) {
            return ServiceResult.fail("회원 정보가 일치하지 않습니다.");
        }
        boardBookmarkRepository.delete(boardBookmark);
        return ServiceResult.success();
    }

    @Override
    public List<Board> postList(String email) {
        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();
        List<Board> boards = boardRepository.findByUser(user);
        return boards;
    }

    @Override
    public List<BoardComment> commentList(String email) {
        Optional<AppUser> optionalAppUser = userRepository.findByEmail(email);
        if (!optionalAppUser.isPresent()) {
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        AppUser user = optionalAppUser.get();

        List<BoardComment> list = boardCommentRepository.findByUser(user);
        return list;
    }

    @Override
    public Board setail(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            throw new BizException("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        return board;
    }


}
