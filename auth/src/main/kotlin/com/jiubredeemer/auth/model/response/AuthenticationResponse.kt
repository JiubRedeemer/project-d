package com.jiubredeemer.auth.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for a dealer's view of a car.")
data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
)
