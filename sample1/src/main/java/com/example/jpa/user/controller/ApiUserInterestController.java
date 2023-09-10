package com.example.jpa.user.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.user.service.UserService;
import com.example.jpa.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiUserInterestController {

    private final UserService userService;

    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestUser(
            @PathVariable Long id,
            @RequestHeader("Z-TOKEN") String token
    ) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.addInterestUser(id, email);
        return ResponseResult.result(result);
    }

    @DeleteMapping("/api/user/interest/{id}")
    public ResponseEntity<?> interestRemoveUser(
            @PathVariable Long id,
            @RequestHeader("Z-TOKEN") String token
    ) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.removeInterestUser(id, email);
        return ResponseResult.result(result);
    }

}
