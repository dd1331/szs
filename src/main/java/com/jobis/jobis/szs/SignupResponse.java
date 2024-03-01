package com.jobis.jobis.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlcklkIiwiaWF0IjoxNzA5Mjk1OTcwLCJleHAiOjE3MDkzODIzNzB9.3z9eSNhyksJofupPm0H6A_uaKa_HiHdZlgd27eyQqK0")
    private String accessToken;
}
