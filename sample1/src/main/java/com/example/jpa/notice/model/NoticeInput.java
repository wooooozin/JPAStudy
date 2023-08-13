package com.example.jpa.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeInput {

    // ID, 제목, 내용, 등록일(작성일)
    @Size(min = 10, max = 100, message = "제목은 10~100자 사이의 값입ㅂ니다.")
    @NotBlank(message = "제목은 필수 항목 입니다.")
    private String title;

    @Size(min = 50, max = 500, message = "내용은 50~500자 사이의 값입ㅂ니다.")
    @NotBlank(message = "내용은 필수 항목 입니다.")
    private String contents;
}
