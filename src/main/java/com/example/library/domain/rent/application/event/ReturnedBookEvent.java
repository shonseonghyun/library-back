package com.example.library.domain.rent.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReturnedBookEvent {
    private Long bookNo;
}

