package com.example.library.domain.user.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinedEvent {
    private Long userNo;
}
