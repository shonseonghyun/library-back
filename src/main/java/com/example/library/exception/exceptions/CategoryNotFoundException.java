package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class CategoryNotFoundException extends AppException {
    public CategoryNotFoundException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
