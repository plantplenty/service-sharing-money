package com.pplenty.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("금액 나누기")
@SpringBootTest
class MoneyDividerTest {

    @Autowired
    MoneyDivider moneyDivider;

    @DisplayName("금액 분배")
    @Test
    void divide() {
        // given
        int amount = 1;
        int numberOfTarget = 3;

        // when
        List<Integer> divide = moneyDivider.divide(amount, numberOfTarget);
        log.info("{}", divide);

        // then
        assertThat(divide.size()).isEqualTo(numberOfTarget);
    }
}