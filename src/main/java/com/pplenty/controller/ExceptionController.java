package com.pplenty.controller;

import com.pplenty.dto.ApiResponse;
import com.pplenty.exception.SharingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yusik on 2020/10/11.
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SharingException.class)
    public ApiResponse<String> sharingException(SharingException e) {
        log.error("error: {}", e.getMessage());
        return ApiResponse.error(e.getCode());
    }
}
