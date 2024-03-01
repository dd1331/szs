package com.jobis.jobis.szs.service;

import com.jobis.jobis.szs.RegistrationValidator;
import com.jobis.jobis.szs.dto.SignupRequest;
import com.jobis.jobis.szs.dto.SignupResponse;
import com.jobis.jobis.szs.entity.User;
import com.jobis.jobis.szs.exception.UnauthorizedAccessException;
import com.jobis.jobis.szs.repository.UserRepository;
import com.jobis.jobis.szs.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RegistrationValidator registrationValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
