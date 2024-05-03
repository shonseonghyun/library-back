package com.example.library.annotation;

import com.example.library.global.mail.domain.mail.enums.MailType;

import java.lang.annotation.*;

@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedNotify {
    MailType type();
}
