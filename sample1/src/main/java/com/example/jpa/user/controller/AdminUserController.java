package com.example.jpa.user.controller;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
