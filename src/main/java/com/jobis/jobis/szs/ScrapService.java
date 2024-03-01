package com.jobis.jobis.szs;

public interface ScrapService {
    public void scrap(ScrapRequest dto, String userId);

    TaxInfo convertToTaxInfo(ScrapResponse jsonData, User user);
}

