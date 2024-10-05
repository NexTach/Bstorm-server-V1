package com.nextech.server.v1.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestMVCController {
    @GetMapping("/test")
    public String test() {
        return "SecurityTest";
    }
}