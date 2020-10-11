package com.pplenty.support;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yusik on 2020/10/11.
 */
@Component
public class MoneyDivider {

    public static Random RANDOM = new Random();

    public List<Integer> divide(int amount, int numberOfTarget) {
        List<Integer> dividedAmounts = new ArrayList<>();
        int balance = amount;

        for (int i = 0; i < numberOfTarget - 1; i++) {
            int dividedAmount = getNextDividedAmount(balance);
            dividedAmounts.add(dividedAmount);
            balance -= dividedAmount;
        }
        dividedAmounts.add(balance);

        return dividedAmounts;
    }

    private int getNextDividedAmount(int balance) {
        return RANDOM.nextInt(balance);
    }
}
