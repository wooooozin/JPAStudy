package com.example.jpa.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogCount {
    private long id;
    private long String;
    private String userName;

    private long noticeCount;
    private long noticeLikesCount;
}
