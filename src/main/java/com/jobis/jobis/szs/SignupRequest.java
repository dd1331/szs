package com.jobis.jobis.szs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String userId;

    private String password;

    private String name;

    // TODO: 이건 유효성 체크 필요?
    private String regNo;


}
