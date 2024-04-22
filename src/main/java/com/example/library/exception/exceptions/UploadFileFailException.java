package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class UploadFileFailException extends AppException {
    public UploadFileFailException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public UploadFileFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
