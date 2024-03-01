package com.jobis.jobis.szs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RegistrationValidator registrationValidator;

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

    public SignupResponse signup(SignupRequest dto) {

        boolean allowed = registrationValidator.isAllowed(dto.getName(), dto.getRegNo());

        if (!allowed) throw new UnauthorizedAccessException("가입이 불가능합니다");

        Optional<User> existingUser = userRepository.findByUserId(dto.getUserId());
        // TODO: custom exception
        if (existingUser.isPresent()) throw new UnauthorizedAccessException("이미 가입된 아이디입니다");


        User user = User.builder()
                .userId(dto.getUserId())
                .name((dto.getName()))
                .regNo(passwordEncoder.encode(dto.getRegNo()))
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return SignupResponse.builder().accessToken(token).build();


    }


}
