package com.pplenty.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pplenty.domain.Distribution;
import com.pplenty.domain.Sharing;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yusik on 2020/10/09.
 */
@Getter
public class SharingResponseDto {

    private final int totalAmount;

    private final int sharedAmount;

    private final List<DistributionDto> sharedDistributions;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime sharedDate;

    public static SharingResponseDto from(Sharing sharing) {

        List<DistributionDto> shared = sharing.getSharedDistributions().stream()
                .map(DistributionDto::from)
                .collect(Collectors.toList());

        int sharedAmount = shared.stream()
                .map(DistributionDto::getAmount)
                .reduce(0, Integer::sum);

        return new SharingResponseDto(
                sharing.getTotalAmount(),
                sharedAmount,
                shared,
                sharing.getCreatedDate()
        );
    }

    public SharingResponseDto(int totalAmount, int sharedAmount, List<DistributionDto> sharedDistributions, LocalDateTime sharedDate) {
        this.totalAmount = totalAmount;
        this.sharedAmount = sharedAmount;
        this.sharedDistributions = sharedDistributions;
        this.sharedDate = sharedDate;
    }

    @Getter
    private static class DistributionDto {

        private final int amount;
        private final long userId;
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        private final LocalDateTime takenDate;

        public static DistributionDto from(Distribution distribution) {
            return new DistributionDto(distribution.getAmount(), distribution.getTakenUserId(), distribution.getTakenDate());
        }

        private DistributionDto(int amount, long userId, LocalDateTime takenDate) {
            this.amount = amount;
            this.userId = userId;
            this.takenDate = takenDate;
        }
    }
}
