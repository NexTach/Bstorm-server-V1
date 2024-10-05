package com.nextech.server.v1.global.security.jwt;

import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.ExpiredTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenException;
import com.nextech.server.v1.global.redis.RedisUtil;
import com.nextech.server.v1.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import static com.nextech.server.v1.global.security.filter.JwtFilter.AUTHORIZATION_HEADER;
import static com.nextech.server.v1.global.security.filter.JwtFilter.BEARER_PREFIX;

@Component
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 60L * 30 * 4; // 2시간
    private static final long REFRESH_TOKEN_TIME = 60L * 60 * 24 * 7; // 1주일
    private static Key key;

    private final AuthDetailsService authDetailsService;
    private final RedisUtil redisUtil;

    @Value("${jwt.secret}")
    private String secretKey;

    public JwtProvider(AuthDetailsService authDetailsService, RedisUtil redisUtil) {
        this.authDetailsService = authDetailsService;
        this.redisUtil = redisUtil;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateTokenDto(String id, Roles roles) {
        String accessToken = generateAccessToken(id);
        String refreshToken = generateRefreshToken(id);
        redisUtil.set(accessToken, id, (int) ACCESS_TOKEN_TIME);
        redisUtil.set(refreshToken, id, (int) REFRESH_TOKEN_TIME);
        return new TokenResponse(
                accessToken,
                refreshToken,
                LocalDateTime.now().plusSeconds(ACCESS_TOKEN_TIME),
                LocalDateTime.now().plusSeconds(REFRESH_TOKEN_TIME),
                roles
        );
    }

    public Long getExpiration(String accessToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        return claims.getExpiration().getTime();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            if (redisUtil.hasKeyBlackList(token)) {
                throw new InvalidTokenException("This token is blacklisted.");
            }
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Token");
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new InvalidTokenException("Invalid Token");
        }

        UserDetails principal = authDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateAccessToken(String id) {
        Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME * 1000);
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, "JWT")
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String id) {
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME * 1000);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void addToBlackList(String token) {
        long expirationTime = getExpiration(token) - System.currentTimeMillis();
        redisUtil.setBlackList(token, "blacklisted", expirationTime);
    }
}