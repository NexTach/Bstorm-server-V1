package com.nextech.server.v1.domain.members.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ResignService {
    void resign(HttpServletRequest request);
}