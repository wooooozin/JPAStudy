package com.example.jpa.notice.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.exception.AlreadyDeletedException;
import com.example.jpa.notice.exception.NoticeNotFoundException;
import com.example.jpa.notice.model.NoticeDeleteInput;
import com.example.jpa.notice.model.NoticeInput;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//    @PostMapping("/api/notice")
//    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
//        Notice notice = Notice.builder()
//                .title(noticeInput.getTitle())
//                .contents(noticeInput.getContents())
//                .regDate(LocalDateTime.now())
//                .hits(0)
//                .likes(0)
//                .build();
//        Notice resultNotice = noticeRepository.save(notice);
//        return resultNotice;
//    }

    @GetMapping("/api/notice/{id}")
    public Notice notice(
            @PathVariable Long id
    ) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        }
        return null;
    }


//    @PutMapping("/api/notice/{id}")
//    public void updateNotice(
//            @PathVariable Long id,
//            @RequestBody NoticeInput noticeInput
//    ) {
//        Optional<Notice> notice = noticeRepository.findById(id);
//        if (notice.isPresent()) {
//            notice.get().setTitle(noticeInput.getTitle());
//            notice.get().setContents(noticeInput.getContents());
//            notice.get().setUpdateDate(LocalDateTime.now());
//            noticeRepository.save(notice.get());
//        }
//    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(
            @PathVariable Long id,
            @RequestBody NoticeInput noticeInput
    ) {
//        Optional<Notice> notice = noticeRepository.findById(id);
//        if (!notice.isPresent()) {
//            // 예외 발생
//            throw new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다.");
//        }
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setContents(noticeInput.getContents());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFountException(NoticeNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(
            @PathVariable Long id
    ) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항이 존재하지 않습니다."));

        notice.setHits(notice.getHits() + 1);
        noticeRepository.save(notice);
    }

//    @DeleteMapping("/api/notice/{id}")
//    public void deleteNotice(@PathVariable Long id) {
//        Notice notice = noticeRepository.findById(id)
//                .orElseThrow(() -> new NoticeNotFoundException("공지사항이 존재 하지 않습니다."));
//        noticeRepository.delete(notice);
//    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항 글이 존재하지 않음"));

        if (notice.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 공지사항 글입니다.");
        }

        notice.setDeleted(true);
        notice.setDeletedDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    @DeleteMapping("/api/notice")
    public void deleteNoticeList(
            @RequestBody NoticeDeleteInput noticeDeleteInput
    ) {
        List<Notice> notices = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(() -> new NoticeNotFoundException("공지사항이 존재하지 않습니다."));
        notices.stream().forEach(e -> {
            e.setDeleted(true);
            e.setDeletedDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(notices);
    }

    @DeleteMapping("/api/notice/all")
    public void deleteAll() {
        noticeRepository.deleteAll();
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    /*
    @PostMapping("/api/notice")
    public void addNotice(@RequestBody NoticeInput noticeInput) {
        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);
    }
     */

    @PostMapping("/api/notice")
    public ResponseEntity<Object> addNotice(
            @RequestBody @Valid NoticeInput noticeInput,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().stream().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .hits(0)
                .likes(0)
                .regDate(LocalDateTime.now())
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> noticeLatest(@PathVariable int size) {
        Page<Notice> notices =
        noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));
        return notices;
    }

}
