package com.schedule.share.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponseHeader {
    private int code; // 응답 코드
    private String message; // 응답 메시지
}