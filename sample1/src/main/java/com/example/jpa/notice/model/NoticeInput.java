package com.example.jpa.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeInput {

    // ID, 제목, 내용, 등록일(작성일)
    @NotBlank(message = "제목은 필수 항목 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 항목 입니다.")
    private String contents;
}
