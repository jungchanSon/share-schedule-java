package com.schedule.share.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

// 클라이언트와 서버 간의 인증 정보 HTTP 헤더
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization"; // 인증 헤더 이름
    private final static String TOKEN_PREFIX = "Bearer "; // 토큰 접두사

    // 응답 헤더에 값을 설정
    public static void set(HttpServletResponse response, String name, String value) {
        response.setHeader(name, value);
    }

    // 요청 헤더에서 값을 가져오기
    public static String get(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    // 요청 헤더에서 모든 값 출력
    public static void printAll(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
    }

    // 요청 헤더에서 액세스 토큰을 가져오기
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}