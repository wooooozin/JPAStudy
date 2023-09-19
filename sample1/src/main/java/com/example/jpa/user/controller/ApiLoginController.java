package com.example.jpa.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.model.UserLogin;
import com.example.jpa.user.model.UserLoginToken;
import com.example.jpa.user.service.UserService;
import com.example.jpa.util.JWTUtils;
import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiLoginController {

    private final UserService userService;

    @PostMapping("/api/longin")
    public ResponseEntity<?> userLogin(
        @RequestBody @Valid UserLogin userLogin,
        Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseResult.fail("입력 값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
        }
        AppUser user = null;

        try {
            user = userService.login(userLogin);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }

        UserLoginToken userLoginToken = JWTUtils.createToken(user);
        if (userLoginToken == null) {
            return ResponseResult.fail("토큰 정보가 존재하지 않습니다.");
        }
        return ResponseResult.success(userLoginToken);

    }
}
