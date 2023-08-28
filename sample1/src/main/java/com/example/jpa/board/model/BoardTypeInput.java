package com.example.jpa.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardTypeInput {

    @NotBlank(message = "게시판 제목은 필수 사항 입니다.")
    private String title;
}
