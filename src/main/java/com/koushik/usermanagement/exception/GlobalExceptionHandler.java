package com.koushik.usermanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> errorHandler(MethodArgumentNotValidException ex
            , HttpServletRequest req){

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(),error.getDefaultMessage());
        });

        ErrorResponse errRes = new ErrorResponse();
        errRes.setStatus(HttpStatus.BAD_REQUEST.value());
        errRes.setTimeStamp(LocalDateTime.now());
        errRes.setPath(req.getRequestURI());
        errRes.setMessage("Validation Error");
        errRes.setError(errors);
        return ResponseEntity.badRequest().body(errRes);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFound(UserNotFoundException ex
            , HttpServletRequest req){

        ErrorResponse errRes = new ErrorResponse();
        errRes.setTimeStamp(LocalDateTime.now());
        errRes.setPath(req.getRequestURI());
        errRes.setStatus(HttpStatus.NOT_FOUND.value());
        errRes.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errRes);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex
            ,HttpServletRequest req){

        ErrorResponse errRes = new ErrorResponse();
        errRes.setTimeStamp(LocalDateTime.now());
        errRes.setPath(req.getRequestURI());
        errRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errRes.setMessage("SomeThing Went Wrong");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errRes);
    }
}
