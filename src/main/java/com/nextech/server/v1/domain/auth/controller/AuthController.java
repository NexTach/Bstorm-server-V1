package com.nextech.server.v1.domain.auth.controller;

import com.nextech.server.v1.domain.auth.dto.request.SignInRequest;
import com.nextech.server.v1.domain.auth.dto.request.SignUpRequest;
import com.nextech.server.v1.domain.auth.dto.response.SignUpResponse;
import com.nextech.server.v1.domain.auth.service.SignInService;
import com.nextech.server.v1.domain.auth.service.SignOutService;
import com.nextech.server.v1.domain.auth.service.SignUpService;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "인증관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final SignOutService signOutService;
    private final JwtTokenService jwtTokenService;

    @Operation(summary = "SignUp", description = "회원가입")
    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }

    @Operation(summary = "Signin", description = "로그인")
    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return signInService.signIn(signInRequest);
    }

    @Operation(summary = "Reissue", description = "토큰 재발급")
    @PatchMapping("/reissue")
    public TokenResponse reissue(@RequestHeader("refreshToken") String refreshToken) {
        return jwtTokenService.reissueToken(refreshToken);
    }

    @Operation(summary = "Signout", description = "로그아웃")
    @DeleteMapping("/signout")
    public HttpEntity<Object> signOut(HttpServletRequest request) {
        signOutService.signOut(request.getHeader("Authorization"));
        return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
    }
}