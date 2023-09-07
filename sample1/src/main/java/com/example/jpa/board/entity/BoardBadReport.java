package com.example.jpa.board.entity;

import com.example.jpa.user.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class BoardBadReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long userId;
    @Column
    private String userName;
    @Column
    private String userEmail;

    @Column
    private long boardId;
    @Column
    private long bardUserId;
    @Column
    private String boardTitle;
    @Column
    private String boardContents;
    @Column
    private LocalDateTime boardRegTime;

    @Column
    private String comments;
    @Column
    LocalDateTime regDate;
}
