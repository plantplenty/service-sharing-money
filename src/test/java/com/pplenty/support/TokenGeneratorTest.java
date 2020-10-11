package com.pplenty.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by yusik on 2020/10/11.
 */
@Slf4j
@DisplayName("토큰 발행")
class TokenGeneratorTest {

    @DisplayName("토큰 생성 테스트")
    @Test
    void generate() {
        // given
        TokenGenerator generator = new TokenGenerator();

        // when
        String token = generator.generate();
        log.info("token: {}", token);

        // then
        assertThat(token.length()).isEqualTo(3);
    }
}