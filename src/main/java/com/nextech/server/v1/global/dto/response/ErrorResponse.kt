package com.nextech.server.v1.global.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

data class ErrorResponse(
    @JsonProperty("http_code") val httpCode: HttpStatus, val message: String
)