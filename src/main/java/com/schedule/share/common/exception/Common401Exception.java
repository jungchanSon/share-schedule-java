package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;

public class Common401Exception extends AbstractException{
    public Common401Exception() {
        super(ResponseCode.UNAUTHORIZED, ResponseCode.UNAUTHORIZED.getMessage() );
    }
}
