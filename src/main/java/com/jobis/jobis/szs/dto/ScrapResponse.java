package com.jobis.jobis.szs.dto;

import lombok.Data;

@Data
public class ScrapResponse {
    private String status;
    private ScrapData data;
    private ScrapErrors scrapErrors;

}
