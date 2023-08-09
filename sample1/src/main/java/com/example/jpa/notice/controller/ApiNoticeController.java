package com.example.jpa.notice.controller;

import com.example.jpa.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiNoticeController {

//    @GetMapping("/api/notice")
//    public String noticeString() {
//        return "공지사항입니다.";
//    }

    @GetMapping("/api/notice")
    public NoticeModel notice() {
        NoticeModel noticeModel = new NoticeModel();
        noticeModel.setId(1);
        noticeModel.setTitle("공지사항 제목 입니다.");
        noticeModel.setContents("공지사항 내용 입니다.");
        noticeModel.setRegDate(LocalDateTime.now());
        return noticeModel;
    }
}
