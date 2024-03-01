package com.jobis.jobis.szs.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CreditCardDeduction {
    private List<Map<String, String>> month;
    private int year;
}
