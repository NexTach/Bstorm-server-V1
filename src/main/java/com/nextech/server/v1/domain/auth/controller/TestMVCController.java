package com.nextech.server.v1.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "테스트", description = "테스트를 위한  SpringMVC Controller")
@Controller
public class TestMVCController {

    @Operation(summary = "JWT Test", description = "JWT 토큰 테스트")
    @GetMapping("/test")
    public String test() {
        return "SecurityTest";
    }
}