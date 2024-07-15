package com.jiubredeemer.auth.model.request

data class AuthenticationRequest(
    val email: String,
    val password: String,
)
