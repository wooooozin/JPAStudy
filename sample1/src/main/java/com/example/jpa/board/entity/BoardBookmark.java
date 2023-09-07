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
public class BoardBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @Column
    private long boardId;
    @Column
    private long boardTypeId;

    @Column
    private String boardTitle;
    @Column
    private String boardUrl;

    @Column
    private LocalDateTime regDate;

}
