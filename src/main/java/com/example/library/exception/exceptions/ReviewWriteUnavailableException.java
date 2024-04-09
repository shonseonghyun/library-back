package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class ReviewWriteUnavailableException extends AppException {
    public ReviewWriteUnavailableException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public ReviewWriteUnavailableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
