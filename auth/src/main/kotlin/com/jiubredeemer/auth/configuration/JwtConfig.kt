package com.jiubredeemer.auth.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "project-d.jwt")
data class JwtConfig(
    val key: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)
