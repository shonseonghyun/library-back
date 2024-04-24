package com.example.library.global.mail.domain.mail.application.event;

import com.example.library.global.mail.domain.mail.application.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendedMailEvent {
    private MailDto mailDto;

}
