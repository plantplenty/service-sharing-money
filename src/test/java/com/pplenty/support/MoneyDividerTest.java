package com.pplenty.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("금액 나누기")
class MoneyDividerTest {

    @DisplayName("인원 수와 분배 수가 같은지 확인")
    @Test
    void checkDividedCount() {
        // given
        MoneyDivider moneyDivider = new MoneyDivider();
        int amount = 100;
        int numberOfTarget = 3;

        // when
        List<Integer> divide = moneyDivider.divide(amount, numberOfTarget);
        log.info("{}", divide);

        // then
        assertThat(divide.size()).isEqualTo(numberOfTarget);
    }

    @DisplayName("분배된 금액의 음수가 없는지 확인")
    @Test
    void checkDividedAmount() {
        // given
        MoneyDivider moneyDivider = new MoneyDivider();
        int amount = 100_000;
        int numberOfTarget = 4;

        // when
        List<Integer> divide = moneyDivider.divide(amount, numberOfTarget);
        log.info("{}", divide);

        // then
        assertThat((int) divide.stream().filter(integer -> integer > 0).count()).isEqualTo(numberOfTarget);
    }
}