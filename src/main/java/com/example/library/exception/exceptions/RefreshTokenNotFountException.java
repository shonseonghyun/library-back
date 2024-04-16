package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class RefreshTokenNotFountException extends AppException {
    public RefreshTokenNotFountException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public RefreshTokenNotFountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
