package com.jiubredeemer.auth.model.response

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
)
