package org.example.medilinkspring.exception;

import org.springframework.http.HttpStatus;
//2xx - 성공:
//OK (200)
//CREATED (201)
//NO_CONTENT (204)
//4xx - 클라이언트 오류:
//BAD_REQUEST (400)
//UNAUTHORIZED (401)
//FORBIDDEN (403)
//NOT_FOUND (404)
//5xx - 서버 오류:
//INTERNAL_SERVER_ERROR (500)
//SERVICE_UNAVAILABLE (503)
public enum ErrorCode {

    COORDINATE_NOT_KOREA("현재 위치가 한국이 아닙니다.", HttpStatus.NOT_FOUND),
    HOSPITAL_NOT_FOUND("병원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    private final String message;
    private final HttpStatus status;
    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    // 상태 코드 가져오기
    public HttpStatus getStatus() {
        return status;
    }
}
