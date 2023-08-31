package com.example.jpa.board.controller;

import com.example.jpa.board.entity.BoardType;
import com.example.jpa.board.model.BoardTypeCount;
import com.example.jpa.board.model.BoardTypeInput;
import com.example.jpa.board.model.BoardTypeUsing;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoard(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors) {
        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());

            return new ResponseEntity<>(ResponseMessage.fail(
                    "입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST
            );
        }
        ServiceResult result = boardService.addBoard(boardTypeInput);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(
        @PathVariable Long id,
        @RequestBody @Valid BoardTypeInput boardTypeInput,
        Errors errors
    ) {
        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());

            return new ResponseEntity<>(ResponseMessage.fail(
                    "입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST
            );
        }

        ServiceResult result = boardService.update(id, boardTypeInput);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(
            @PathVariable Long id
    ) {
        ServiceResult result = boardService.deleteBoard(id);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type")
    public ResponseEntity<?> boardType() {
        List<BoardType> boardTypeList = boardService.getAllType();

        return ResponseEntity.ok().body(ResponseMessage.success(boardTypeList));
    }

    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(
            @PathVariable Long id,
            @RequestBody BoardTypeUsing boardTypeUsing
    ) {
        ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success(result));
    }

    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount() {
        List<BoardTypeCount> boardTypeCounts = boardService.getBoardTypeCount();
        return ResponseEntity.ok().body(ResponseMessage.success(boardTypeCounts));
    }
}