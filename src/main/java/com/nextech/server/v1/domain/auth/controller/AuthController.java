package com.nextech.server.v1.domain.auth.controller;

import com.nextech.server.v1.domain.auth.dto.request.SignInRequest;
import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.ReissueResponse;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.ReissueService;
import com.nextech.server.v1.domain.auth.service.SignInService;
import com.nextech.server.v1.domain.auth.service.SignOutService;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final SignOutService signOutService;
    private final ReissueService reissueService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return signInService.signIn(signInRequest);
    }

    @PatchMapping("/reissue")
    public ReissueResponse reissue(@RequestHeader("refreshToken") String refreshToken) {
        return reissueService.reissue(refreshToken);
    }

    @DeleteMapping("/signout")
    public HttpEntity<Object> signOut(HttpServletRequest request) {
        signOutService.signOut(request.getHeader("Authorization"));
        return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
    }
}