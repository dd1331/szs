package com.jobis.jobis.szs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "userId는 공백일 수 없습니다.")
    @Schema(example = "testUserId")
    private String userId;

    @NotBlank(message = "password는 공백일 수 없습니다.")
    @Schema(example = "123456789")
    private String password;

}