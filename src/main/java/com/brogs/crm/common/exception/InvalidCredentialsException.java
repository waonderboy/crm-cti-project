package com.brogs.crm.common.exception;


import com.brogs.crm.common.response.ErrorCode;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }

    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidCredentialsException(String errorMsg) {
        super(errorMsg, ErrorCode.INVALID_CREDENTIALS);
    }

    public InvalidCredentialsException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
