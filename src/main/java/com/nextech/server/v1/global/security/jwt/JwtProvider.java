package com.nextech.server.v1.global.security.jwt;

import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.dto.response.TokenResponse;
import com.nextech.server.v1.global.enums.Roles;
import com.nextech.server.v1.global.exception.ExpiredRefreshTokenException;
import com.nextech.server.v1.global.exception.ExpiredTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenException;
import com.nextech.server.v1.global.exception.InvalidTokenFormatException;
import com.nextech.server.v1.global.security.auth.AuthDetailsService;
import com.nextech.server.v1.global.security.jwt.entity.RefreshToken;
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
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
    private static final long ACCESS_TOKEN_TIME = 60L * 30 * 4;
    private static final long REFRESH_TOKEN_TIME = 60L * 60 * 24 * 7;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final AuthDetailsService authDetailsService;
    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    public JwtProvider(RefreshTokenRepository refreshTokenRepository, MemberRepository memberRepository, AuthDetailsService authDetailsService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
        this.authDetailsService = authDetailsService;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateTokenDto(String username, Roles roles) {
        String accessToken = generateAccessToken(username, roles);
        String refreshToken = generateRefreshToken(username);

        RefreshToken refreshTokenEntity = new RefreshToken(refreshToken, username, LocalDateTime.now().plusSeconds(REFRESH_TOKEN_TIME));
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenResponse(accessToken, refreshToken, LocalDateTime.now().plusSeconds(ACCESS_TOKEN_TIME), LocalDateTime.now().plusSeconds(REFRESH_TOKEN_TIME), roles);
    }

    private String generateAccessToken(String username, Roles roles) {
        Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME * 1000);
        return Jwts.builder().setSubject(username).claim(AUTHORITIES_KEY, roles).setIssuedAt(new Date()).setExpiration(accessTokenExpiresIn).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    private String generateRefreshToken(String username) {
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME * 1000);
        return Jwts.builder().setSubject(username).setExpiration(refreshTokenExpiresIn).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public TokenResponse reissue(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findById(refreshToken).orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));
        if (storedToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredRefreshTokenException("Refresh token expired");
        }
        Members member = memberRepository.findByEmail(storedToken.getUsername());
        if (member == null) {
            throw new InvalidTokenException("User not found");
        }
        String newAccessToken = generateAccessToken(member.getEmail(), member.getRole());
        String newRefreshToken = generateRefreshToken(member.getEmail());
        refreshTokenRepository.delete(storedToken);
        RefreshToken newRefreshTokenEntity = new RefreshToken(newRefreshToken, member.getEmail(), LocalDateTime.now().plusSeconds(REFRESH_TOKEN_TIME));
        refreshTokenRepository.save(newRefreshTokenEntity);
        return new TokenResponse(newAccessToken, newRefreshToken, LocalDateTime.now().plusSeconds(ACCESS_TOKEN_TIME), LocalDateTime.now().plusSeconds(REFRESH_TOKEN_TIME), member.getRole());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        UserDetails userDetails = authDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        throw new InvalidTokenFormatException("Invalid token format");
    }

    public Long getExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        return expiration.getTime();
    }
}