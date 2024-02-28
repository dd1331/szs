package com.jobis.jobis.szs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "userId는 공백일 수 없습니다.")
    private String userId;

    @NotBlank(message = "password는 공백일 수 없습니다.")
    @Size(min = 6, message = "password는 최소 6글자 이상이어야 합니다.")
    private String password;

}