package com.jobis.jobis.szs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new UnauthorizedAccessException("로그인 실패");
        }

        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow();
        String token = jwtService.generateToken(user);

        return LoginResponse.builder().accessToken(token).build();
    }



}
