package com.jobis.jobis.szs;

public interface ScrapService {
    public void scrap(ScrapRequest dto, String userId);

    TaxInfo format(DeductionData jsonData, User user);
}

