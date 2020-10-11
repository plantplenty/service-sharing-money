package com.pplenty.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by yusik on 2020/10/11.
 */
@Getter
@RequiredArgsConstructor
public enum SharingExceptionCode {

    DUPLICATED_TAKING("뿌려진 금액은 한번만 받을 수 있습니다."),
    CANNOT_SELF_TAKING("자신이 뿌린 금액은 받을 수 없습니다."),
    NO_ACCESS_ROOM("해당 요청에 대한 권한이 없습니다."),
    EXPIRED_DISTRIBUTION("기간이 만료되어 가져갈 수 없습니다."),
    FINISH_DISTRIBUTION("모두 분배가 완료된 토큰입니다."),
    NO_TOKEN("유효하지 않은 토큰입니다."),

    NO_AUTHENTICATION("해당 토큰에 접근 권한이 없습니다."),
    EXPIRED_TOKEN("조회 기간이 만료된 토큰 입니다."),
    ;

    private final String message;
}
