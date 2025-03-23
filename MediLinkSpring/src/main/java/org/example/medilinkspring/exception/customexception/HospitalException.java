package org.example.medilinkspring.exception.customexception;

import org.example.medilinkspring.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class HospitalException extends RuntimeException{
    private final ErrorCode errorCode;
    public HospitalException(ErrorCode errorCode, Exception e) {
        super(errorCode.getMessage(),e); // 메시지를 super로 전달
        this.errorCode = errorCode;
    }

    public HospitalException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 메시지를 super로 전달
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }
}
