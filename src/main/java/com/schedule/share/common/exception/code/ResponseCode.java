package com.schedule.share.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "성공"),
    UNAUTHORIZED("0001", "인증 실패"),
    FORBIDDEN("0002", "권한 없음"),
    BAD_REQUEST("0003", "잘못된 요청입니다."),
    NOT_FOUND("0004", "조회된 데이터가 없습니다."),
    FAIL("9999", "ERROR");

    private final String code;
    private final String message;
}
