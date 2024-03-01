package com.jobis.jobis.szs;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
 class ScrapResponse {
    private String status;
    private ScrapData data;
    private Errors errors;

}
@Data
class ScrapData {
    private int 종합소득금액;
    private String 이름;
    private IncomeDeduction 소득공제;

}
@Data
class IncomeDeduction {
    private List<NationalPensionDeduction> 국민연금;
    private CreditCardDeduction 신용카드소득공제;
    private String 세액공제;

}
@Data
class NationalPensionDeduction {
    private String 월;
    private String 공제액;

}
@Data
class CreditCardDeduction {
    private List<Map<String, String>> month;
    private int year;

}
@Data
class Errors {
    private String code;
    private String message;
    private String validations;

}



@RequiredArgsConstructor
@Service
public class ScrapServiceImpl implements ScrapService {
    private final TaxInfoRepository taxInfoRepository;
    private final UserRepository userRepository;

    public TaxInfo convertToTaxInfo(ScrapResponse scrapResponse, User user) {


        ScrapData scrapData = scrapResponse.getData();
        IncomeDeduction 소득공제 = scrapData.get소득공제();
        List<NationalPensionDeduction> 국민연금 = 소득공제.get국민연금();

        BigDecimal 세액공제 = new BigDecimal(소득공제.get세액공제().replace(",", ""));

        BigDecimal 종합소득 = new BigDecimal(scrapData.get종합소득금액());

        BigDecimal 국민연금공제총액 = 국민연금.stream()
                .map(NationalPensionDeduction::get공제액).map(공제액 -> new BigDecimal(공제액.replaceAll(",", ""))).reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Map<String, String>> 신용카드소득공제월목록 = 소득공제.get신용카드소득공제().getMonth();


        BigDecimal 신용카드공제총액 = 신용카드소득공제월목록.stream().flatMap(map -> map.values().stream()).map(value -> new BigDecimal(value.replaceAll(",", ""))).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal 총소득공제 = 신용카드공제총액.add(국민연금공제총액);


        return TaxInfo.builder().taxYear(소득공제.get신용카드소득공제().getYear()).user(user).소득공제(총소득공제).세액공제(세액공제).종합소득금액(종합소득).build();


    }


    public void scrap(ScrapRequest dto, String userId) {
        String url = "https://codetest-v4.3o3.co.kr";
        ScrapRequest requestBody = new ScrapRequest(dto.getName(), dto.getRegNo());
        HttpClient httpClient = HttpClient.create().headers(httpHeaders -> httpHeaders.set("X-API-KEY", "oKJg+BDBIjKkayD9DgNHNQ==")).secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        WebClient webClient = WebClient.builder().baseUrl(url).defaultHeader("Content-Type", "application/json").clientConnector(connector).build();

        Mono<ScrapResponse> responseMono = webClient.method(HttpMethod.POST).uri("/scrap").body(BodyInserters.fromValue(requestBody)).retrieve()
                .bodyToMono(ScrapResponse.class);

        responseMono.subscribe(response -> {

            if (response.getStatus().equals("fail")) throw new UnauthorizedAccessException("스크랩 실패");
            System.out.println("응답 받음: " + response);
            User user = userRepository.findByUserId(userId).orElseThrow();
            Optional<TaxInfo> existing = taxInfoRepository.findByUserIdOrderByTaxYearDesc(user.getId());

            TaxInfo taxInfo = convertToTaxInfo(response, user);

            existing.ifPresent(info -> taxInfo.setId(info.getId()));

            taxInfoRepository.save(taxInfo);

        }, error -> {
            System.err.println("에러 발생: " + error.getMessage());
        });


    }




}

