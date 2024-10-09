package com.nextech.server.v1.domain.members.service;

import com.nextech.server.v1.global.enums.Roles;
import jakarta.servlet.http.HttpServletRequest;

public interface RoleChangeService {
    void changeRole(HttpServletRequest request, String currentPassword, Roles role);
}
