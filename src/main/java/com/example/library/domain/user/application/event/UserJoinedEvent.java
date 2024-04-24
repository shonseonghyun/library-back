package com.example.library.domain.user.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinedEvent {
    private Long userNo;
}
