package com.pplenty.repository;

import com.pplenty.domain.Sharing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by yusik on 2020/10/12.
 */
@SpringBootTest
class SharingRepositoryTest {

    private static final long CREATOR_USER_ID = 314;
    private static final long CREATOR_ROOM_ID = 333;

    @Autowired
    private SharingRepository sharingRepository;

    @AfterEach
    void tearDown() {
        sharingRepository.deleteAll();
    }

    @DisplayName("토큰으로 Sharing 을 조회한다")
    @Test
    void findByToken() {
        // given
        int totalAmount = 10000;
        String token = "ABC";
        sharingRepository.save(Sharing.builder()
                .token(token).totalAmount(totalAmount)
                .userId(CREATOR_USER_ID)
                .roomId(CREATOR_ROOM_ID)
                .distributions(emptyList())
                .createdDate(LocalDateTime.now())
                .build());

        // when
        Sharing sharing = sharingRepository.findByToken(token);

        // then
        assertThat(sharing.getToken()).isEqualTo(token);
        assertThat(sharing.getTotalAmount()).isEqualTo(totalAmount);

    }
}