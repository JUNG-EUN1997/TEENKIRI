package com.beyond.board_demo.qna.dto;

import com.beyond.board_demo.qna.domain.QnA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnAListResDto {
    private Long id;
    private String questionUserName;
    private String title;
    private LocalDateTime createdTime;
}
