package com.pplenty.service;

import com.pplenty.domain.Sharing;
import com.pplenty.dto.SharingHeaderDto;
import com.pplenty.dto.SharingRequestDto;
import com.pplenty.dto.SharingResponseDto;
import com.pplenty.exception.SharingException;
import com.pplenty.exception.SharingExceptionCode;
import com.pplenty.repository.SharingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by yusik on 2020/10/11.
 */
@SpringBootTest
class SharingServiceTest {

    private static final long CREATOR_USER_ID = 314;
    private static final long TAKEN_USER_ID = 126;
    private static final long CREATOR_ROOM_ID = 333;

    @Autowired
    private SharingService sharingService;

    @Autowired
    private SharingRepository sharingRepository;

    @AfterEach
    void tearDown() {
        sharingRepository.deleteAll();
    }

    @DisplayName("뿌리기를 생성한다")
    @Test
    void generateSharing() {
        // given
        int expectedSize = 5;
        SharingRequestDto requestDto = SharingRequestDto.of(1000, expectedSize, CREATOR_USER_ID, CREATOR_ROOM_ID);

        // when
        Sharing sharing = sharingService.generateSharing(requestDto);

        // then
        assertThat(sharing.getUserId()).isEqualTo(CREATOR_USER_ID);
        assertThat(sharing.getRoomId()).isEqualTo(CREATOR_ROOM_ID);
        assertThat(sharing.getDistributions()).hasSize(expectedSize);
    }

    @DisplayName("뿌리기를 가져간다")
    @Test
    void takeMoney() {

        // given
        Sharing sharing = sharingService.generateSharing(SharingRequestDto.of(1000, 5, CREATOR_USER_ID, CREATOR_ROOM_ID));

        // when
        long amount = sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID));

        // then
        assertThat(amount).isGreaterThanOrEqualTo(0);
    }

    @DisplayName("뿌리기 조회")
    @Test
    void getTakenStatusByToken() {

        // given
        int total = 1000;
        int numberOfTarget = 5;
        Sharing sharing = sharingService.generateSharing(SharingRequestDto.of(total, numberOfTarget, CREATOR_USER_ID, CREATOR_ROOM_ID));
        long amount = sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID));

        // when
        SharingResponseDto responseDto = sharingService.getTakenStatusByToken(sharing.getToken(), SharingHeaderDto.of(CREATOR_USER_ID, CREATOR_ROOM_ID));

        // then
        assertThat(responseDto.getSharedDistributions()).hasSize(1);
        assertThat(responseDto.getSharedAmount()).isEqualTo(amount);
        assertThat(responseDto.getTotalAmount()).isEqualTo(total);
    }

    @DisplayName("없는 토큰 조회 오류")
    @Test
    void noToken() {
        // given
        String token = "NO!";

        // when & then
        assertThatThrownBy(() -> sharingService.takeMoney(token, SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID)))
                .isInstanceOf(SharingException.class)
                .hasMessage(SharingExceptionCode.NO_TOKEN.getMessage());
    }

    @DisplayName("자신이 뿌린 돈 가져가기 요청하면 에러")
    @Test
    void cannotSelfTaking() {
        // given
        int total = 1000;
        int numberOfTarget = 5;
        Sharing sharing = sharingService.generateSharing(SharingRequestDto.of(total, numberOfTarget, CREATOR_USER_ID, CREATOR_ROOM_ID));

        // when & then
        assertThatThrownBy(() -> sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(CREATOR_USER_ID, CREATOR_ROOM_ID)))
                .isInstanceOf(SharingException.class)
                .hasMessage(SharingExceptionCode.CANNOT_SELF_TAKING.getMessage());
    }

    @DisplayName("다른 방에 뿌린돈 가져가기 오류")
    @Test
    void noAccessRoom() {
        // given
        int total = 1000;
        int numberOfTarget = 5;
        Sharing sharing = sharingService.generateSharing(SharingRequestDto.of(total, numberOfTarget, CREATOR_USER_ID, CREATOR_ROOM_ID));

        // when & then
        assertThatThrownBy(() -> sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID + 1)))
                .isInstanceOf(SharingException.class)
                .hasMessage(SharingExceptionCode.NO_ACCESS_ROOM.getMessage());
    }

    @DisplayName("같은 사람이 두번 가져가기 요청")
    @Test
    void duplicatedTaking() {
        // given
        int total = 1000;
        int numberOfTarget = 5;
        Sharing sharing = sharingService.generateSharing(SharingRequestDto.of(total, numberOfTarget, CREATOR_USER_ID, CREATOR_ROOM_ID));

        // when & then
        assertThatThrownBy(() -> {
            sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID));
            sharingService.takeMoney(sharing.getToken(), SharingHeaderDto.of(TAKEN_USER_ID, CREATOR_ROOM_ID));
        })
                .isInstanceOf(SharingException.class)
                .hasMessage(SharingExceptionCode.DUPLICATED_TAKING.getMessage());
    }
}