package com.jiubredeemer.auth.model

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
)
