package com.informationsecuritylab1.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)

data class LoginResponse(val token: String)

data class RegisterRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)
