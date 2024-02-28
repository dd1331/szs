package com.jobis.jobis.szs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/szs")
@RequiredArgsConstructor
public class SzsController {

    private final JwtAuthService jwtAuthService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest dto) {
        System.out.println("test");
        System.out.println(dto);
        SignupResponse res = jwtAuthService.signup(dto);

        return ResponseEntity.ok(res);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {

        LoginResponse res = jwtAuthService.login(dto);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/test")
    public ResponseEntity<String> login2() {

        return ResponseEntity.ok("dd");
    }
}
