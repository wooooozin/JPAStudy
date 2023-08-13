package com.example.jpa.user.controller;

import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.UserInput;
import com.example.jpa.user.model.UserUpdate;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiUserController {
    private final UserRepository userRepository;

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        AppUser user = AppUser.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(userInput.getPassword())
                .phone(userInput.getPhone())
                .regDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdate userUpdate,
            Errors errors
            ) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        user.setPhone(userUpdate.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
