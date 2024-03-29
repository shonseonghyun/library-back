package com.example.library.domain.heart.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckBookExistEvent {
    private Long bookNo;
}
