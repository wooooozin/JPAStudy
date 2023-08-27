package com.example.jpa.user.controller;

import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.entity.UserLoginHistory;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.user.model.UserSearch;
import com.example.jpa.user.model.UserStatusInput;
import com.example.jpa.user.model.UserSummary;
import com.example.jpa.user.repository.UserLoginRepository;
import com.example.jpa.user.repository.UserRepository;
import com.example.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AdminUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserLoginRepository userLoginRepository;

    private final UserService userService;

    //    @GetMapping("/api/admin/user")
//    public ResponseMessage userList() {
//        List<AppUser> userList = userRepository.findAll();
//        long totalUserCont = userRepository.count();
//
//        return ResponseMessage.builder()
//                .totalCount(totalUserCont)
//                .data(userList)
//                .build();
//    }


    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(
            @PathVariable Long id
    ) {
        Optional<AppUser> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(ResponseMessage.success(user));
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch) {
        // email like '%' || ...
        // email like concat..

        List<AppUser> userList = userRepository.findByEmailContainsOrUserNameContainsOrPhoneNotContains(
                userSearch.getEmail(),
                userSearch.getEmail(),
                userSearch.getPhone()
        );

        return ResponseEntity.ok().body(ResponseMessage.success(userList));
    }

    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> userStatus(
            @PathVariable Long id,
            @RequestBody UserStatusInput userStatusInput
    ) {
        Optional<AppUser> optionalAppUser = userRepository.findById(id);
        if (!optionalAppUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        AppUser user = optionalAppUser.get();
        user.setStatus(userStatusInput.getStatus());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ) {
        Optional<AppUser> optionalAppUser = userRepository.findById(id);
        if (!optionalAppUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        AppUser user = optionalAppUser.get();
        if (noticeRepository.countByUser(user) > 0) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 글이 있습니다."), HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> userLoginHistory() {
        List<UserLoginHistory> userLoginHistories = userLoginRepository.findAll();
        return ResponseEntity.ok().body(userLoginHistories);
    }

    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> userLock(
            @PathVariable Long id
    ) {
        Optional<AppUser> optionalAppUser = userRepository.findById(id);
        if (!optionalAppUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        AppUser user = optionalAppUser.get();
        if (user.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("이미 제한된 사용자 입니다."), HttpStatus.BAD_REQUEST);
        }
        user.setLockYn(true);
        userRepository.save(user);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> userUnlock(
            @PathVariable Long id
    ) {
        Optional<AppUser> optionalAppUser = userRepository.findById(id);
        if (!optionalAppUser.isPresent()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        AppUser user = optionalAppUser.get();
        if (!user.isLockYn()) {
            return new ResponseEntity<>(ResponseMessage.fail("접속제한 대상이 아닙니다."), HttpStatus.BAD_REQUEST);
        }
        user.setLockYn(false);
        userRepository.save(user);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/admin/user/status/count")
    public ResponseEntity<?> userStatusCount() {
        UserSummary userSummary = userService.getUserStatusCount();

        return ResponseEntity.ok().body(ResponseMessage.success(userSummary));
    }

    @GetMapping("/api/admin/user/today")
    public ResponseEntity<?> todayUser() {
        List<AppUser> users = userService.getTodayUsers();
        return ResponseEntity.ok().body(ResponseMessage.success(users));
    }
}
