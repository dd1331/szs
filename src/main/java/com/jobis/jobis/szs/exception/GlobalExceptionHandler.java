package com.jobis.jobis.szs.exception;

import com.jobis.jobis.szs.exception.UnauthorizedAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        // 예외 처리 로직을 작성
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseBody
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        // 권한 없음 예외 처리 로직을 작성
        return new ResponseEntity<>("Unauthorized access: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
