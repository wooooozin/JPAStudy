package com.example.jpa.notice.entity;

import com.example.jpa.user.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @Column
    private String title;
    @Column
    private String contents;
    @Column
    private LocalDateTime regDate;
    @Column
    private LocalDateTime updateDate;

    @Column
    private LocalDateTime deletedDate;

    @Column
    private int hits;
    @Column
    private int likes;

    @Column
    private boolean deleted;
}
