package com.schedule.share.common.exception.handler;

import com.schedule.share.common.exception.AbstractException;
import com.schedule.share.common.exception.Common400Exception;
import com.schedule.share.common.exception.Common401Exception;
import com.schedule.share.common.exception.Common403Exception;
import com.schedule.share.common.exception.Common404Exception;
import com.schedule.share.common.exception.Common500Exception;
import com.schedule.share.common.exception.code.ResponseCode;
import com.schedule.share.common.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value=Common400Exception.class)
    ResponseModel<Object> handler400(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {Exception.class, RuntimeException.class})
    ResponseModel<Object> native400(RuntimeException exception){
        exception.printStackTrace();

        return ResponseModel.of( new Common400Exception(ResponseCode.NOT_FOUND, ResponseCode.NOT_FOUND.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value={Common401Exception.class})
    ResponseModel<Object> handler401(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value= Common403Exception.class)
    ResponseModel<Object> handler403(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value={Common404Exception.class})
    ResponseModel<Object> handler404(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value={Common500Exception.class})
    ResponseModel<Object> handler500(AbstractException exception){
        return response(exception);
    }
    ResponseModel<Object> response(AbstractException exception) {
        exception.printStackTrace();

        return ResponseModel.of(exception);
    }
}
