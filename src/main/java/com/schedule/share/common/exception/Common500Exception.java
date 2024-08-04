package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;

public class Common500Exception extends AbstractException{
    public Common500Exception() {
        super(ResponseCode.FAIL, ResponseCode.FAIL.getMessage() );
    }
}
