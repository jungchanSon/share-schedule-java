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
    ResponseModel<?> handler400(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {Exception.class, RuntimeException.class})
    ResponseModel<?> native400(RuntimeException exception){
        exception.printStackTrace();
        Common400Exception common400Exception;

        if (exception.getMessage().isEmpty()) {
            common400Exception = new Common400Exception(ResponseCode.NOT_FOUND, ResponseCode.NOT_FOUND.getMessage());
        } else {
            common400Exception = new Common400Exception(ResponseCode.NOT_FOUND, exception.getMessage());
        }

        return ResponseModel.of( common400Exception );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value={Common401Exception.class})
    ResponseModel<?> handler401(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value= Common403Exception.class)
    ResponseModel<?> handler403(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value={Common404Exception.class})
    ResponseModel<?> handler404(AbstractException exception){
        return response(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value={Common500Exception.class})
    ResponseModel<?> handler500(AbstractException exception){
        return response(exception);
    }
    ResponseModel<?> response(AbstractException exception) {
        exception.printStackTrace();

        return ResponseModel.of(exception);
    }
}
