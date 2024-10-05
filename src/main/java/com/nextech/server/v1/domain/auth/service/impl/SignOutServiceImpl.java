package com.nextech.server.v1.domain.auth.service.impl;

import com.nextech.server.v1.domain.auth.service.SignOutService;
import com.nextech.server.v1.domain.members.entity.Members;
import com.nextech.server.v1.domain.members.repository.MemberRepository;
import com.nextech.server.v1.global.redis.RedisUtil;
import com.nextech.server.v1.global.security.jwt.JwtProvider;
import com.nextech.server.v1.global.security.jwt.repository.RefreshTokenRepository;
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
    private final JwtProvider jwtProvider;

    @Override
    public void signOut(String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Members members = memberRepository.findByEmail(email);
        if (members == null) {
            throw new UsernameNotFoundException("User not found");
        }
        refreshTokenRepository.deleteById(Objects.requireNonNull(refreshTokenRepository.findByUsername(members.getEmail())).getRefreshToken());
        redisUtil.setBlackList(accessToken, members.getEmail(), jwtProvider.getExpiration(accessToken));
    }
}