package com.nextech.server.v1.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextech.server.v1.global.dto.response.ErrorResponse;
import com.nextech.server.v1.global.exception.ExpiredTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenFormatException;
import com.nextech.server.v1.global.security.jwt.service.JwtAuthenticationService;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenService jwtTokenService;
    private final JwtAuthenticationService jwtAuthenticationService;

    public JwtFilter(JwtTokenService jwtTokenService, JwtAuthenticationService jwtAuthenticationService) {
        this.jwtTokenService = jwtTokenService;
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/v1/auth/signup") || requestURI.startsWith("/api/v1/auth/signin") || requestURI.startsWith("/api/v1/auth/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = jwtTokenService.resolveToken(request);
            if (StringUtils.hasText(jwt) && jwtTokenService.validateToken(jwt)) {
                Authentication authentication = jwtAuthenticationService.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (InvalidTokenFormatException | InvalidTokenException | ExpiredTokenException e) {
            setErrorResponse(response, e.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseBody);
    }
}