package com.schedule.share.common.response;

import java.util.HashMap;
import java.util.Map;

public record ApiResponse<T>(ApiResponseHeader header, Map<String, T> body) {

    // 응답 상태 코드 및 메시지 상수 정의
    private final static int SUCCESS = 200; // 성공 상태 코드
    private final static int NOT_FOUND = 400; // 리소스를 찾지 못한 경우의 상태 코드
    private final static int FAILED = 500; // 서버 오류 상태 코드
    private final static String SUCCESS_MESSAGE = "SUCCESS"; // 성공 메시지
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND"; // 리소스를 찾지 못한 경우의 메시지
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다."; // 서버 오류 메시지
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token."; // 유효하지 않은 접근 토큰 메시지
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token."; // 유효하지 않은 리프레시 토큰 메시지
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet."; // 토큰이 아직 만료되지 않은 경우의 메시지

    // 성공응답
    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);
        return new ApiResponse<>(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    // 실패 응답
    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<>(new ApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }

    // 찾지 못할 때 응답
    public static <T> ApiResponse<T> notFound() {
        return new ApiResponse<>(new ApiResponseHeader(NOT_FOUND, NOT_FOUND_MESSAGE), null);
    }

    // 유효하지 않은 접근 토큰 응답
    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse<>(new ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null);
    }

    // 유효하지 않은 리프레시 토큰 응답
    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse<>(new ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    // 토큰이 아직 만료되지 않은 경우의 응답을 생성
    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse<>(new ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }
}
