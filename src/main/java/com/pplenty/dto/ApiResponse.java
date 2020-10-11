package com.pplenty.dto;

import com.pplenty.exception.SharingExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by yusik on 2020/10/09.
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiResponse<T> {

    private final static String OK = "OK";
    private final static String SUCCESS = "success";

    private final String code;
    private final String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(OK, SUCCESS, data);
    }

    public static <T> ApiResponse<T> error(SharingExceptionCode code) {
        return new ApiResponse<>(code.name(), code.getMessage());
    }

}
