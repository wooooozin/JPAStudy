package com.example.jpa.notice.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;

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
    public List<NoticeInput> notice() {
        List<NoticeInput> noticeModelList = new ArrayList<>();
        return noticeModelList;
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {
        return 10;
    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(
//            @RequestParam String title,
//            @RequestParam String contents
//    ) {
//        NoticeModel noticeModel = NoticeModel.builder()
//                .id(1)
//                .title(title)
//                .contents(contents)
//                .regDate(LocalDateTime.now())
//                .build();
//        return noticeModel;
//    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(NoticeModel noticeModel) {
//        noticeModel.setId(2);
//        noticeModel.setRegDate(LocalDateTime.now());
//        return noticeModel;
//    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(@RequestBody NoticeModel noticeModel) {
//        noticeModel.setId(3);
//        noticeModel.setRegDate(LocalDateTime.now());
//        return noticeModel;
//    }

//    @PostMapping("/api/notice")
//    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
//        Notice notice = Notice.builder()
//                .title(noticeInput.getTitle())
//                .contents(noticeInput.getContents())
//                .regDate(LocalDateTime.now())
//                .build();
//        noticeRepository.save(notice);
//
//        return notice;
//    }

    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .build();
        Notice resultNotice = noticeRepository.save(notice);
        return resultNotice;
    }

}
