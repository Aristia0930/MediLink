package org.example.medilinkspring.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor //기본생성자 자동생성
public class ErrorResponse {
    private String message; //에러메시지
    private int status; //http 상태코드
    private LocalDateTime timestamp;

    public ErrorResponse(String s, int value, LocalDateTime now) {
        this.message=s;
        this.status=value;
        this.timestamp=now;
    }

    public ErrorResponse(ErrorCode errorCode, LocalDateTime timestamp) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value(); // HttpStatus의 value() 메서드를 통해 상태 코드 값 사용
        this.timestamp = timestamp;
    }
}
