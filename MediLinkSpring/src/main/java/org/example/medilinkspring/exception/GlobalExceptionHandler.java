package org.example.medilinkspring.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.exception.customexception.HospitalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse errorResponse= new ErrorResponse("예상하지 못한 오류발생.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());

        log.debug("예상치 못한 에러 발생");
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);

    }

    @ExceptionHandler(HospitalException.class)
    public ResponseEntity<ErrorResponse> HospitalException(HospitalException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse= new ErrorResponse(errorCode,
                LocalDateTime.now());

        log.error("병원컨트롤러에서 예외 발생 원인:{}",ex.getCause());


        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);

    }
    
    
}
