package com.pplenty.exception;

import lombok.Getter;

/**
 * Created by yusik on 2020/10/11.
 */
@Getter
public class SharingException extends RuntimeException {

    private final SharingExceptionCode code;

    public SharingException(SharingExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
