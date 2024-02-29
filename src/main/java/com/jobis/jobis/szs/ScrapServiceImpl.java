package com.jobis.jobis.szs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLContext;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.*;

@Data
 class DeductionData {
    private String status;
    private DeductionDetail data;
    private Errors errors;

}
@Data
class DeductionDetail {
    private int 종합소득금액;
    private String 이름;
    private IncomeDeduction 소득공제;

    // Getter and Setter
}
@Data
class IncomeDeduction {
    private List<NationalPensionDeduction> 국민연금;
    private CreditCardDeduction 신용카드소득공제;
    private String 세액공제;

    // Getter and Setter
}
@Data
class NationalPensionDeduction {
    private String 월;
    private String 공제액;

    // Getter and Setter
}
@Data
class CreditCardDeduction {
    private List<Map<String, String>> month;
    private int year;

    // Getter and Setter
}
@Data
class Errors {
    private String code;
    private String message;
    private String validations;

    // Getter and Setter
}



@RequiredArgsConstructor
@Service
public class ScrapServiceImpl implements  ScrapService {

//    private final RestTemplate restTemplate;

    //    public ScrapServiceImpl( RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
    public void format(DeductionData deductionData) {


        List<NationalPensionDeduction> national = deductionData.getData().get소득공제().get국민연금();
        System.out.println("deductionData "+national);

        String 세액공제 = deductionData.getData().get소득공제().get세액공제();


        // TODO: check type
        int 종합소득 = deductionData.getData().get종합소득금액();

        BigDecimal totalNational =  national.stream() // TODO: check type
                .map(NationalPensionDeduction::get공제액) // 공제액만 가져오기
                .map(공제액 -> new BigDecimal(공제액.replaceAll(",", "")))
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 모든 공제액을 합산

        List<Map<String, String>> credit = deductionData.getData().get소득공제().get신용카드소득공제().getMonth();



        BigDecimal totalCredit = credit.stream()
                .flatMap(map -> map.values().stream())
                .map(value -> new BigDecimal(value.replaceAll(",", "")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal 총소득공제 = totalCredit.add(totalNational);
        BigDecimal 과세표준 = (new BigDecimal(종합소득).subtract(총소득공제));

        get기본세율(종합소득);

        // TODO: 숫자 타입 확인
        System.out.println("totalCredit "+totalCredit );
        System.out.println("totalNational "+totalNational );
        System.out.println("세액공제 "+세액공제 );
        System.out.println("종합소득 "+종합소득 );
        System.out.println("과세표준 "+과세표준);
    }

    private void  get기본세율(int 종합소득){


    }
    private DeductionData parseJsonData(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            DeductionData deductionData = objectMapper.readValue(jsonData, DeductionData.class);
            System.out.println("@@@"+deductionData);
            return deductionData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public void scrap2(){
        String url = "https://codetest-v4.3o3.co.kr";
        ScrapRequest requestBody = new ScrapRequest("동탁", "921108-1582816");
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-API-KEY", "oKJg+BDBIjKkayD9DgNHNQ==");
        // SSL 인증 무시를 위한 TrustManager 설정
        HttpClient httpClient = HttpClient.create()
                .headers(httpHeaders -> httpHeaders.set("X-API-KEY", "oKJg+BDBIjKkayD9DgNHNQ=="))
                .secure(sslContextSpec -> sslContextSpec.sslContext(
                        SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                ));

        // WebClient 인스턴스 생성
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(connector)
                .build();

        // 요청 본문 생성

        // POST 요청 보내기
        Mono<String> responseMono = webClient.method(HttpMethod.POST)
                .uri("/scrap")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
//                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("HTTP error")))
                .bodyToMono(String.class);

        // 응답 처리
        responseMono.subscribe(
                response -> {

                    System.out.println("응답 받음: " + response);
                    // 응답 처리
                },
                error -> {
                    System.err.println("에러 발생: " + error.getMessage());
                    // 에러 처리
                }
        );

        // 블로킹되지 않도록 메인 스레드가 종료되지 않게 하기 위해 대기
        try {
            Thread.sleep(5000); // 5초 동안 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void scrap(){
        HttpHeaders headers = new HttpHeaders();
        System.out.println("@@@@@@@@@@@@@@@@2312");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", "oKJg+BDBIjKkayD9DgNHNQ==");

        ScrapRequest requestBody = new ScrapRequest("동탁", "921108-1582816");

        HttpEntity<ScrapRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        String url = "https://codetest-v4.3o3.co.kr/scrap";

        TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null; // Not relevant.
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // Do nothing. Just allow them all.
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // Do nothing. Just allow them all.
            }
        } };

        HostnameVerifier trustAllHostnames = (String hostname, SSLSession session) -> true // Just
                // allow
                // them
                // all.
                ;

        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }

        ignoreCertificates();
        RestTemplate restTemplate = new RestTemplate();
        try {

        String response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
        System.out.println("Response: " + response);
        } catch (RestClientException err) {

            System.out.println("@@@@@@@@@@@@@@@@"+err);

        }

    }

    private void ignoreCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {


        }}

}

