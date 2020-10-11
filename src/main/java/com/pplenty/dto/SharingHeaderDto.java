package com.pplenty.dto;

import lombok.Getter;

/**
 * Created by yusik on 2020/10/11.
 */
@Getter
public class SharingHeaderDto {
    private final long userId;
    private final long roomId;

    private SharingHeaderDto(long userId, long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public static SharingHeaderDto of(long userId, long roomId) {
        return new SharingHeaderDto(userId, roomId);
    }
}
