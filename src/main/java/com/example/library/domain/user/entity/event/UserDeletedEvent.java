package com.example.library.domain.user.entity.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeletedEvent {
    private Long userNo;
}
