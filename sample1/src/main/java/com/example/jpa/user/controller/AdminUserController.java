package com.example.jpa.user.controller;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.user.model.UserSearch;
import com.example.jpa.user.model.UserStatusInput;
import com.example.jpa.user.repository.UserRepository;
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
}
