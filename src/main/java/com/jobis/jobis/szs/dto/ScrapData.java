package com.jobis.jobis.szs.dto;

import lombok.Data;

@Data
public class ScrapData {
    private int 종합소득금액;
    private String 이름;
    private IncomeDeduction 소득공제;

}
