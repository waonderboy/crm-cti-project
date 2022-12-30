package com.brogs.crm.common.exception;

import com.brogs.crm.common.response.ErrorCode;

public class ExpiredCredentialsException extends BaseException{
    public ExpiredCredentialsException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }

    public ExpiredCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExpiredCredentialsException(String errorMsg) {
        super(errorMsg, ErrorCode.EXPIRED_REFRESH_TOKEN);
    }

    public ExpiredCredentialsException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
