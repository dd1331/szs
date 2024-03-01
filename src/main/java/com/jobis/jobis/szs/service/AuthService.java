package com.jobis.jobis.szs.service;

import com.jobis.jobis.szs.dto.LoginRequest;
import com.jobis.jobis.szs.dto.LoginResponse;

public interface AuthService {
    public LoginResponse login(LoginRequest dto);
}
