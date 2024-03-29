package com.example.library.domain.heart.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckUserExistEvent {
    private Long userNo;
}
