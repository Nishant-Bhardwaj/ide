package com.sql.ide.exception;

import com.sql.ide.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorMsg(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .build(),
                HttpStatus.BAD_REQUEST
        );

    }


}
