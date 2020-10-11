package com.pplenty.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by yusik on 2020/10/09.
 */
@NoArgsConstructor
@Getter
public class SharingRequestDto {

    private int amount;
    private int numberOfTarget;
    private SharingHeaderDto sharingHeaderDto;

    public static SharingRequestDto of(int amount, int numberOfTarget, long userId, long roomId) {
        return new SharingRequestDto(amount, numberOfTarget, SharingHeaderDto.of(userId, roomId));
    }
    private SharingRequestDto(int amount, int numberOfTarget, SharingHeaderDto sharingHeaderDto) {
        this.amount = amount;
        this.numberOfTarget = numberOfTarget;
        this.sharingHeaderDto = sharingHeaderDto;
    }

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
