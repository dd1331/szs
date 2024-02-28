package com.jobis.jobis.szs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


// TODO: 예외를 http로 변경필요
// 허용되는건 디비말고 그냥 메모리에 올려버림 몇개 안돼서

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private static final Map<String, String> allowedMembers = new HashMap<>();

    static {
        allowedMembers.put("동탁", "921108-1582816");
        allowedMembers.put("관우", "681108-1582816");
        allowedMembers.put("손권", "890601-2455116");
        allowedMembers.put("유비", "790411-1656116");
        allowedMembers.put("조조", "810326-2715702");
    }

    public boolean isMemberAllowed(String name, String id) {
        String registeredId = allowedMembers.get(name);

        return registeredId != null && registeredId.equals(id);
    }

    public LoginResponse login(LoginRequest dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword());
        authenticationManager.authenticate(authToken);
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow();
        String token = jwtService.generateToken(user);

        return LoginResponse.builder().accessToken(token).build();
    }

    public SignupResponse signup(SignupRequest dto) {

        boolean allowed = isMemberAllowed(dto.getName(), dto.getRegNo());

        if (!allowed) throw new UnauthorizedAccessException("가입이 불가능합니다");

        Optional<User> existingUser = userRepository.findByUserId(dto.getUserId());
        // TODO: custom exception
        if (existingUser.isPresent()) throw new UnauthorizedAccessException("이미 가입된 아이디입니다");


        User user = User.builder()
                .userId(dto.getUserId())
                .name((dto.getName()))
                .regNo(dto.getRegNo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return SignupResponse.builder().accessToken(token).build();


    }




}
