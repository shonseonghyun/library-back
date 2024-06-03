package com.example.library.exception.exceptions;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;

public class ReviewAlreadyExistsByBookNoException extends AppException {
    public ReviewAlreadyExistsByBookNoException(ErrorCode errorCode, String msg) {
        super(errorCode, msg);
    }

    public ReviewAlreadyExistsByBookNoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
