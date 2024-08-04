package com.schedule.share.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.schedule.share.common.exception.AbstractException;
import com.schedule.share.common.exception.code.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
@Getter
public class ResponseModel<T> {
    private T data;
    private String code;
    private String message;
    private Pagination pagination;

    public static <T> ResponseModel<T> of(T data, long total , int limit, int offset) {

        Pagination pagination = Pagination.builder()
                .total(total)
                .limit(limit)
                .offset(offset)
                .build()
                .calc();

        ResponseModel<T> result = (ResponseModel<T>) ResponseModel.builder()
                .data(data)
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .pagination(pagination)
                .build();

        return result ;
    }

    public static <T> ResponseModel<T> of(T data) {
        long total = 0;
        int limit = 1;
        int offset = 0;

        Pagination pagination = Pagination.builder()
                .total(total)
                .limit(limit)
                .offset(offset)
                .build()
                .calc();

        ResponseModel<T> result = (ResponseModel<T>) ResponseModel.builder()
                .data(data)
                .pagination(pagination)
                .build();

        return result ;
    }

    public static ResponseModel<Object> of(AbstractException exception) {
        return ResponseModel.builder()
                .code(exception.getCode().getCode())
                .message(exception.getMessage())
                .build();
    }

    @Builder
    @ToString
    @Getter
    public static class Pagination {
        private long total;
        private int limit;
        private int offset;
        private String sort;

        private int totalPages;
        private int page;
        private int size;
        private boolean isFirst;
        private boolean isLast;

        Pagination calc() {
            this.totalPages = (int) (total/limit + (total % limit == 0 ? 0:1));
            this.page = (offset / limit) + 1;
            this.size = limit;
            this.isFirst = page == 1;
            this.isLast = page == totalPages;

            return this;
        }
    }
}