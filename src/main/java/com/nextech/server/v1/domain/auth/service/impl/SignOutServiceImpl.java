package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.service.SignOutService;
import com.nextech.server.v1.global.members.entity.Members;
import com.nextech.server.v1.global.members.repository.MemberRepository;
import com.nextech.server.v1.global.redis.RedisUtil;
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository;
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SignOutServiceImpl implements SignOutService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisUtil redisUtil;
    private final JwtTokenService jwtTokenService;

    @Transactional
    @Override
    public void signOut(String bearerToken) {
        String accessToken = jwtTokenService.resolveToken(bearerToken);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Members members = memberRepository.findByPhoneNumber(email);
        if (members == null) {
            throw new UsernameNotFoundException("User not found");
        }
        refreshTokenRepository.deleteById(
                Objects.requireNonNull(refreshTokenRepository.
                        findByUsername(members.getPhoneNumber())).getRefreshToken());
        redisUtil.setBlackList(
                accessToken,
                members.getPhoneNumber(),
                jwtTokenService.getExpiration(accessToken)
        );
    }
}