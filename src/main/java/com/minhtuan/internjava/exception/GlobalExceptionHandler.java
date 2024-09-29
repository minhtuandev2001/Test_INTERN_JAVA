package com.minhtuan.internjava.exception;

import com.minhtuan.internjava.dto.response.ResponseObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject<?>> handleGlobalException(AppException aex){

        return ResponseEntity.status(aex.getStatus()).body(
               new ResponseObject<String>(
                       aex.getStatus(),
                       aex.getMessages()
               )
        );
    }
    // Yêu cầu của body bị thiếu


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> FiledRequiredException(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject<String>(
                        400,
                        Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()
                )
        );
    }


}
