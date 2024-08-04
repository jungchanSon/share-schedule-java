package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;

public class Common403Exception extends AbstractException{
    public Common403Exception() {
        super(ResponseCode.FORBIDDEN, ResponseCode.FORBIDDEN.getMessage() );
    }
}
