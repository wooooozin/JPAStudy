package com.example.jpa.notice.controller;

import com.example.jpa.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiNoticeController {

//    @GetMapping("/api/notice")
//    public String noticeString() {
//        return "공지사항입니다.";
//    }

    //    @GetMapping("/api/notice")
//    public NoticeModel notice() {
//        NoticeModel noticeModel = new NoticeModel();
//        noticeModel.setId(1);
//        noticeModel.setTitle("공지사항 제목 입니다.");
//        noticeModel.setContents("공지사항 내용 입니다.");
//        noticeModel.setRegDate(LocalDateTime.now());
//        return noticeModel;
//    }
/*
    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {
        List<NoticeModel> noticeModelList = new ArrayList<>();
        NoticeModel noticeModel1 = new NoticeModel();
        noticeModel1.setId(1);
        noticeModel1.setTitle("공지사항 입니다.");
        noticeModel1.setContents("공지사항 내용입니다.");
        noticeModel1.setRegDate(LocalDateTime.of(2023, 1, 31, 0, 0));
        noticeModelList.add(noticeModel1);

        NoticeModel noticeModel2 = NoticeModel.builder()
                .id(2)
                .title("두번째 공지사항 입니다.")
                .contents("두번째 공지사항 내용 입니다.")
                .regDate(LocalDateTime.of(2023, 1, 31, 0, 0))
                .build();
        noticeModelList.add(noticeModel2);

        return noticeModelList;
    }
 */
    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {
        List<NoticeModel> noticeModelList = new ArrayList<>();
        return noticeModelList;
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {
        return 10;
    }

    @PostMapping("/api/notice")
    public NoticeModel addNotice(
            @RequestParam String title,
            @RequestParam String contents
    ) {
        NoticeModel noticeModel = NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();
        return noticeModel;
    }

}
