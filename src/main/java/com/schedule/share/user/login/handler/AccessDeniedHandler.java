// 아직 사용하지 않음
//package com.schedule.share.user.login.handler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import java.io.IOException;
//
//// 403 접근 거부 응답을 처리
//@Component
//@RequiredArgsConstructor
//public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
//
//    private final HandlerExceptionResolver handlerExceptionResolver;
//
//    // 사용자가 권한이 없는 리소스에 접근하려고 할 때 호출
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
//    }
//}
