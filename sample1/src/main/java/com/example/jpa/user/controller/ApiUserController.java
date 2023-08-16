package com.example.jpa.user.controller;

import com.example.jpa.notice.entity.Notice;
import com.example.jpa.notice.model.NoticeResponse;
import com.example.jpa.notice.model.ResponseError;
import com.example.jpa.notice.repository.NoticeRepository;
import com.example.jpa.user.entity.AppUser;
import com.example.jpa.user.exception.ExistsEmailException;
import com.example.jpa.user.exception.PasswordNotMatchException;
import com.example.jpa.user.exception.UserNotFoundException;
import com.example.jpa.user.model.*;
import com.example.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final NoticeRepository noticeRepository;

    /*
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
     */

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

    @GetMapping("/api/user/{id}")
    public UserResponse getUser(
            @PathVariable Long id
    ) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // UserResponse userResponse = new UserResponse(user);
        UserResponse userResponse = UserResponse.of(user);

        return userResponse;
    }


    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> userNotice(
            @PathVariable Long id
    ) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> notices = noticeRepository.findByUser(user);
        List<NoticeResponse> noticeResponses = new ArrayList<>();
        notices.stream().forEach((e) -> {
            noticeResponses.add(NoticeResponse.of(e));
        });

        return noticeResponses;
    }

//    @PostMapping("/api/user")
//    public ResponseEntity<?> addUser(
//            @RequestBody UserInput userInput,
//            Errors errors
//    ) {
//        List<ResponseError> responseErrors = new ArrayList<>();
//        if (errors.hasErrors()) {
//            errors.getAllErrors().stream().forEach((e) -> {
//                responseErrors.add(ResponseError.of((FieldError) e));
//            });
//            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
//        }
//
//        if (userRepository.countByEmail(userInput.getEmail()) > 0) {
//            throw new ExistsEmailException("이미 가입된 이메일 주소입니다.");
//        }
//
//        AppUser user = AppUser.builder()
//                .email(userInput.getEmail())
//                .userName(userInput.getUserName())
//                .phone(userInput.getPhone())
//                .password(userInput.getPassword())
//                .regDate(LocalDateTime.now())
//                .build();
//        userRepository.save(user);
//        return ResponseEntity.ok().build();
//    }

    @ExceptionHandler(ExistsEmailException.class)
    public ResponseEntity<?> ExistsEmailExceptionHandler(ExistsEmailException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable Long id,
            @RequestBody @Valid UserInputPassword userInputPassword,
            Errors errors
    ) {
        List<ResponseError> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        AppUser user = userRepository.findByIdAndPassword(id, userInputPassword.getPassword())
                .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));

        user.setPassword(userInputPassword.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> passwordNotMatchExceptionHandler(PasswordNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> addUser(
            @RequestBody UserInput userInput,
            Errors errors
    ) {
        List<ResponseError> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach((e) -> {
                responseErrors.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.countByEmail(userInput.getEmail()) > 0) {
            throw new ExistsEmailException("이미 가입된 이메일 주소입니다.");
        }
        String encryptPassword = getEncryptPassword(userInput);

        AppUser user = AppUser.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .phone(userInput.getPhone())
                .password(encryptPassword)
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private static String getEncryptPassword(UserInput userInput) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(userInput.getPassword());
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        // 작성한 공지사항이 있는 경우 - 계정 삭제 불가, 공지사항 삭제하고 탈퇴 시도해줘
        // 아니면 공지사항을 다 삭제하겠다..
        // 아니면 게시글은 유지하되 계정정보만 탈퇴한 회원으로 변경?

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            String message = "제약 조건에 문제가 발생했습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String message = "회원 탈퇴 중 문제가 발생했습니다.";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/user/find")
    public ResponseEntity<?> findUser(
            @RequestBody UserInputFind userInputFind
    ) {
        AppUser user = userRepository.findByUserNameAndPhone(userInputFind.getUserName(), userInputFind.getPhone())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        UserResponse userResponse = UserResponse.of(user);
        return ResponseEntity.ok().body(userResponse);
    }

}
