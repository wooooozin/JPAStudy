package com.example.jpa.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardTypeCount {

    private long id;
    private String boardName;
    private LocalDateTime regDate;
    private boolean usingYn;
    private long boardCount;

    public BoardTypeCount(Object[] objects) {
        this.id = ((BigInteger) objects[0]).longValue();
        this.boardName = (String) objects[1];
        this.regDate = ((Timestamp) objects[2]).toLocalDateTime();
        this.usingYn = (Boolean)objects[3];
        this.boardCount = ((BigInteger)objects[4]).longValue();
    }

    public BoardTypeCount(BigInteger id, String boardName, Timestamp regDate, Boolean usingYn, BigInteger boardCount) {
        this.id = id.longValue();
        this.boardName = boardName;
        this.regDate = regDate.toLocalDateTime();
        this.usingYn = usingYn;
        this.boardCount = boardCount.longValue();
    }
}
