package com.pplenty.service;

import com.pplenty.domain.Distribution;
import com.pplenty.domain.Sharing;
import com.pplenty.domain.Taking;
import com.pplenty.dto.SharingHeaderDto;
import com.pplenty.dto.SharingRequestDto;
import com.pplenty.dto.SharingResponseDto;
import com.pplenty.exception.SharingException;
import com.pplenty.exception.SharingExceptionCode;
import com.pplenty.repository.SharingRepository;
import com.pplenty.repository.TakingRepository;
import com.pplenty.support.MoneyDivider;
import com.pplenty.support.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by yusik on 2020/10/09.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SharingService {

    private final SharingRepository sharingRepository;
    private final TakingRepository takingRepository;
    private final MoneyDivider moneyDivider;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public Sharing generateSharing(SharingRequestDto requestDto) {

        ArrayList<Distribution> distributions = new ArrayList<>();
        List<Integer> dividedAmounts = moneyDivider.divide(requestDto.getAmount(), requestDto.getNumberOfTarget());
        for (Integer amount : dividedAmounts) {
            distributions.add(Distribution.builder()
                    .amount(amount)
                    .build());
        }

        return sharingRepository.save(Sharing.builder()
                .token(tokenGenerator.generate())
                .userId(requestDto.getUserId())
                .roomId(requestDto.getRoomId())
                .totalAmount(requestDto.getAmount())
                .distributions(distributions)
                .createdDate(LocalDateTime.now())
                .build());
    }

    @Transactional
    public long takeMoney(String token, SharingHeaderDto sharingHeaderDto) {

        Sharing sharing = sharingRepository.findByToken(token);
        validate(sharingHeaderDto, sharing);

        Distribution preparedDistribution = sharing.getDistributions().stream()
                .filter(Distribution::isReady)
                .findFirst()
                .orElseThrow(() -> new SharingException(SharingExceptionCode.FINISH_DISTRIBUTION));

        take(sharingHeaderDto, sharing, preparedDistribution);

        return preparedDistribution.getAmount();
    }

    @Transactional(readOnly = true)
    public SharingResponseDto getTakenStatusByToken(String token, SharingHeaderDto sharingHeaderDto) {
        Sharing sharing = sharingRepository.findByToken(token);
        if (!sharing.isSameUser(sharingHeaderDto.getUserId())) {
            throw new SharingException(SharingExceptionCode.NO_AUTHENTICATION);
        }

        return SharingResponseDto.from(sharing);
    }

    private void take(SharingHeaderDto sharingHeaderDto, Sharing sharing, Distribution preparedDistribution) {
        try {
            takingRepository.save(Taking.builder()
                    .takenUserId(sharingHeaderDto.getUserId())
                    .distribution(preparedDistribution)
                    .sharing(sharing)
                    .createdDate(LocalDateTime.now())
                    .build());
        } catch (DuplicateKeyException e) {
            throw new SharingException(SharingExceptionCode.DUPLICATED_TAKING);
        }
    }

    private void validate(SharingHeaderDto sharingHeaderDto, Sharing sharing) {
        if (Objects.isNull(sharing)) {
            throw new SharingException(SharingExceptionCode.NO_TOKEN);
        }

        if (sharing.isSameUser(sharingHeaderDto.getUserId())) {
            throw new SharingException(SharingExceptionCode.CANNOT_SELF_TAKING);
        }

        if (sharing.isDifferentRoom(sharingHeaderDto.getRoomId())) {
            throw new SharingException(SharingExceptionCode.NO_ACCESS_ROOM);
        }

        if (sharing.isDifferentRoom(sharingHeaderDto.getRoomId())) {
            throw new SharingException(SharingExceptionCode.NO_ACCESS_ROOM);
        }

        if (sharing.isExpired(LocalDateTime.now())) {
            throw new SharingException(SharingExceptionCode.EXPIRED_DISTRIBUTION);
        }
    }
}
