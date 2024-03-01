package com.jobis.jobis.szs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @Schema(example = "testUserId")
    private String userId;

    @Schema(example = "123456789")
    private String password;

    @Schema(example = "동탁")
    private String name;

    @Schema(example = "921108-1582816")
    private String regNo;


}
