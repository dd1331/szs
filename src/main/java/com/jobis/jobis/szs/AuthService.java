package com.jobis.jobis.szs;

public interface AuthService {
    public LoginResponse login(LoginRequest dto);
    public SignupResponse signup(SignupRequest dto) throws Exception;
}