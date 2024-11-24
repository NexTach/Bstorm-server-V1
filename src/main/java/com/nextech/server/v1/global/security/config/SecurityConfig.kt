package com.nextech.server.v1.global.security.config

import com.nextech.server.v1.global.security.auth.exception.CustomAccessDeniedHandler
import com.nextech.server.v1.global.security.filter.JwtFilter
import com.nextech.server.v1.global.security.jwt.service.JwtAuthenticationService
import com.nextech.server.v1.global.security.jwt.service.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
open class SecurityConfig(
    private val jwtTokenService: JwtTokenService,
    private val jwtAuthenticationService: JwtAuthenticationService
) {

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun securityFilterChain(http: HttpSecurity, accessDeniedHandler: CustomAccessDeniedHandler): SecurityFilterChain {
        http.csrf { it.disable() }.cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(
                    "/auth/signin",
                    "/auth/signup",
                    "/auth/reissue"
                ).permitAll()
                    .requestMatchers(
                        "/members/all",
                        "/admin/**",
                        "/log"
                    ).hasAnyRole(
                            "ADMIN",
                            "DEVELOPER"
                        )
                    .requestMatchers(
                        "/members/ward").hasAnyRole(
                            "ADMIN",
                            "DEVELOPER",
                            "PROTECTOR"
                        )
                    .requestMatchers(
                        "/members/protector").hasAnyRole(
                            "ADMIN",
                            "DEVELOPER",
                            "WARD_0",
                            "WARD_1",
                            "WARD_2",
                            "WARD_3"
                        )
                    .requestMatchers("/missions/list")
                    .hasAnyRole("ADMIN", "DEVELOPER")
                    .requestMatchers("/missions/custom")
                    .hasAnyRole("ADMIN", "PROTECTOR", "DEVELOPER")
                    .anyRequest().authenticated()
            }.exceptionHandling{
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .addFilterBefore(
                JwtFilter(jwtTokenService,jwtAuthenticationService),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("Authorization", "Content-Type", "refreshToken")
            allowCredentials = false
            exposedHeaders = listOf("Authorization")
        }
        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", corsConfig)
        }
        return source
    }
}