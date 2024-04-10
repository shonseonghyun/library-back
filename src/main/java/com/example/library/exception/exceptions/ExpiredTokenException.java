package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class ExpiredTokenException extends AppException {
    public ExpiredTokenException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
