package com.jobis.jobis.szs.service;

import com.jobis.jobis.szs.dto.ScrapRequest;
import com.jobis.jobis.szs.dto.ScrapResponse;
import com.jobis.jobis.szs.entity.TaxInfo;
import com.jobis.jobis.szs.entity.User;

public interface ScrapService {
    public void scrap(ScrapRequest dto, String userId);

    TaxInfo convertToTaxInfo(ScrapResponse jsonData, User user);
}

