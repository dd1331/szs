package com.jobis.jobis.szs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScrapServiceImplTest {


    @Autowired
    private ScrapService scrapService;


    @Test
    public void testScrap() {
        ScrapRequest dto = new ScrapRequest();
        scrapService.scrap(dto, "test");
    }

    @Test
    public  void test() {

        String jsonData = "{\"status\":\"success\",\"data\":{\"종합소득금액\":20000000,\"이름\":\"동탁\",\"소득공제\":{\"국민연금\":[{\"월\":\"2023-01\",\"공제액\":\"300,000.25\"},{\"월\":\"2023-02\",\"공제액\":\"200,000\"},{\"월\":\"2023-03\",\"공제액\":\"400,000.75\"},{\"월\":\"2023-05\",\"공제액\":\"100,000.10\"},{\"월\":\"2023-06\",\"공제액\":\"300,000\"},{\"월\":\"2023-08\",\"공제액\":\"200,000.20\"},{\"월\":\"2023-09\",\"공제액\":\"300,000.40\"},{\"월\":\"2023-10\",\"공제액\":\"300,000.70\"},{\"월\":\"2023-11\",\"공제액\":\"0\"},{\"월\":\"2023-12\",\"공제액\":\"0\"}],\"신용카드소득공제\":{\"month\":[{\"01\":\"100,000.10\"},{\"03\":\"100,000.20\"},{\"05\":\"200,000.30\"},{\"10\":\"100,000\"},{\"12\":\"300,000.50\"}],\"year\":2023},\"세액공제\":\"300,000\"}},\"errors\":{\"code\":null,\"message\":null,\"validations\":null}}";
        ScrapResponse scrapResponse = parseJsonData(jsonData);
        scrapService.convertToTaxInfo(scrapResponse, new User());


    }

    private ScrapResponse parseJsonData(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ScrapResponse scrapResponse = objectMapper.readValue(jsonData, ScrapResponse.class);
            return scrapResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


