package com.example.jpa.user.controller;

import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.ResponseMessage;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminUserController {

    private final UserRepository userRepository;

    @GetMapping("/api/admin/user")
    public ResponseMessage userList() {
        List<AppUser> userList = userRepository.findAll();
        long totalUserCont = userRepository.count();

        return ResponseMessage.builder()
                .totalCount(totalUserCont)
                .data(userList)
                .build();
    }


    @GetMapping("/api/admin/user/{id}/")
    public void userDetail(
            @PathVariable Long id
    ) {
        
    }
}
