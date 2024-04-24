package com.example.library.domain.review.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewWriteReqDto {
    @NotNull
    private String reviewContent;
}
