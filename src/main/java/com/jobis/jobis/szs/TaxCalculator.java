package com.jobis.jobis.szs;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {

    private static final BigDecimal[] THRESHOLDS = {
            new BigDecimal("14000000"),  // 1400만원 이하
            new BigDecimal("50000000"),  // 1400만원 초과~5000만원 이하
            new BigDecimal("88000000"),  // 5000만원 초과~8800만원 이하
            new BigDecimal("150000000"), // 8800만원 초과~1억5천만원 이하
            new BigDecimal("300000000"), // 1억5천만원 초과~3억원 이하
            new BigDecimal("500000000"), // 3억원 초과~5억원 이하
            new BigDecimal("1000000000") // 5억원 초과~10억원 이하
    };

    private static final BigDecimal[] RATES = {
            new BigDecimal("0.06"),   // 1400만원 이하
            new BigDecimal("0.15"),   // 1400만원 초과~5000만원 이하
            new BigDecimal("0.24"),   // 5000만원 초과~8800만원 이하
            new BigDecimal("0.35"),   // 8800만원 초과~1억5천만원 이하
            new BigDecimal("0.38"),   // 1억5천만원 초과~3억원 이하
            new BigDecimal("0.40"),   // 3억원 초과~5억원 이하
            new BigDecimal("0.42"),    // 5억원 초과~10억원 이하
            new BigDecimal("0.45")    // 10억원 초과
    };

    private static final BigDecimal[] BASE_AMOUNTS = {
            BigDecimal.ZERO,                    // 1400만원 이하
            new BigDecimal("840000"),           // 1400만원 초과~5000만원 이하
            new BigDecimal("6240000"),          // 5000만원 초과~8800만원 이하
            new BigDecimal("15360000"),         // 8800만원 초과~1억5천만원 이하
            new BigDecimal("37060000"),         // 1억5천만원 초과~3억원 이하
            new BigDecimal("94060000"),         // 3억원 초과~5억원 이하
            new BigDecimal("174060000"),         // 5억원 초과~10억원 이하
            new BigDecimal("384060000")         // 10억원 초과
    };


    public static BigDecimal calculateTax(BigDecimal taxableIncome) {
        BigDecimal tax = BigDecimal.ZERO;
        for (int i = 0; i < THRESHOLDS.length; i++) {
            if (taxableIncome.compareTo(THRESHOLDS[i]) <= 0) {
                if (i > 0) {
                    BigDecimal excessAmount = taxableIncome.subtract(THRESHOLDS[i - 1]);
                    System.out.println("@@@@"+excessAmount);
                    tax = BASE_AMOUNTS[i].add(excessAmount.multiply(RATES[i]));
                } else {
                    tax = taxableIncome.multiply(RATES[i]);
                }
                return tax.setScale(0, RoundingMode.HALF_UP);
            }
        }
        // 10억원 초과
        BigDecimal excessAmount = taxableIncome.subtract(THRESHOLDS[THRESHOLDS.length - 1]);
        return BASE_AMOUNTS[BASE_AMOUNTS.length - 1].add(excessAmount.multiply(RATES[RATES.length-1]))
                .setScale(0, RoundingMode.HALF_UP);
    }


}
