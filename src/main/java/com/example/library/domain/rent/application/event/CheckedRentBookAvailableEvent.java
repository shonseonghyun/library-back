package com.example.library.domain.rent.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckedRentBookAvailableEvent {
    private Long bookNo;
}
