package com.schedule.share.common.exception;

import com.schedule.share.common.exception.code.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
abstract public class AbstractException extends RuntimeException{

    private final ResponseCode code;
    private final String message;
}
