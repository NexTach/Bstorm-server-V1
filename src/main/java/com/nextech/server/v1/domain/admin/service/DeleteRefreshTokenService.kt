package com.nextech.server.v1.domain.admin.service

import com.nextech.server.v1.domain.admin.dto.request.DeleteRefreshTokenRequest

interface DeleteRefreshTokenService {
    fun deleteRefreshToken(toUser: DeleteRefreshTokenRequest)
}