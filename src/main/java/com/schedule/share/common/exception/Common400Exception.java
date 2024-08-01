package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;

public class Common400Exception extends AbstractException{
    public Common400Exception() {
        super(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage());
    }

    public Common400Exception(ResponseCode code, String message) {
        super(code, message);
    }
}
