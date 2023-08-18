package com.example.jpa.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserLogin {
    @NotBlank(message = "이메일 항복은 필수 입니다.")
    private String email;

    @NotBlank(message = "비밀번호 항목은 필수 입니다.")
    private String password;

}
