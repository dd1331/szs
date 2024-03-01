package com.jobis.jobis.szs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/szs")
@RequiredArgsConstructor
public class SzsController {

    private final JwtAuthService jwtAuthService;
    private final ScrapService scrapService;
    private final UserService userService;
    private final TaxService taxService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest dto) {
        SignupResponse res = userService.signup(dto);

        return ResponseEntity.ok(res);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {

        LoginResponse res = jwtAuthService.login(dto);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/scrap")
    public void scrap(@RequestBody ScrapRequest dto, Principal connectedUser) {
        scrapService.scrap(dto,connectedUser.getName());
    }


    @PostMapping("/refund")
    public ResponseEntity<FinalTaxResponse> getFinalTax(Principal principal) {
        FinalTaxResponse res = taxService.getFinalTax(principal.getName());
        return ResponseEntity.ok(res);
    }
}
