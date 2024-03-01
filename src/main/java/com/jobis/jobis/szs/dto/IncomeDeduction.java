package com.jobis.jobis.szs.dto;

import lombok.Data;

import java.util.List;

@Data
public class IncomeDeduction {
    private List<NationalPensionDeduction> 국민연금;
    private CreditCardDeduction 신용카드소득공제;
    private String 세액공제;

}
