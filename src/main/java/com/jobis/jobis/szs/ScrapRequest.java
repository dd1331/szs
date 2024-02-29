package com.jobis.jobis.szs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapRequest {
    private String name;

    private String regNo;

    // 생성자, 게터, 세터
}