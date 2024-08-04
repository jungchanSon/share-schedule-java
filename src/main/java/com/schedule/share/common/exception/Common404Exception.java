package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;

public class Common404Exception extends AbstractException{
    public Common404Exception() {
        super(ResponseCode.NOT_FOUND, ResponseCode.NOT_FOUND.getMessage() );
    }
}
