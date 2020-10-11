package com.pplenty.dto;

import com.pplenty.domain.Sharing;
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

    private final String code;
    private final String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("ok", "success", data);
    }
    public static <T> ApiResponse<T> error(SharingExceptionCode code) {
        return new ApiResponse<>(code.name(), code.getMessage());
    }

}
