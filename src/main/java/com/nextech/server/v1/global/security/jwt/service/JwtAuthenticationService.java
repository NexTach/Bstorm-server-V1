package com.nextech.server.v1.global.security.jwt.service;

import com.nextech.server.v1.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtAuthenticationService {
    private final AuthDetailsService authDetailsService;
    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationService(AuthDetailsService authDetailsService, JwtTokenService jwtTokenService) {
        this.authDetailsService = authDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    public Authentication getAuthentication(String token) {
        Key key = (Key) jwtTokenService.getKey();
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        UserDetails userDetails = authDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}