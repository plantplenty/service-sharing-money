package com.pplenty.dto;

import lombok.Getter;

/**
 * Created by yusik on 2020/10/09.
 */
@Getter
public class SharingRequestDto {

    private int amount;
    private int numberOfTarget;
    private SharingHeaderDto sharingHeaderDto;

    public void setHeaders(long userId, long roomId) {
        this.sharingHeaderDto = SharingHeaderDto.of(userId, roomId);
    }

    public long getUserId() {
        return sharingHeaderDto.getUserId();
    }

    public long getRoomId() {
        return sharingHeaderDto.getRoomId();
    }
}
