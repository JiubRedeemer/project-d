package com.jiubredeemer.auth.service


import com.jiubredeemer.auth.configuration.JwtConfig
import com.jiubredeemer.auth.model.AuthenticationResponse
import com.jiubredeemer.auth.model.request.AuthenticationRequest
import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.repository.RefreshTokenRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtConfig,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val registrationService: RegistrationService,
) {

    fun registration(request: UserRegistration): AuthenticationResponse {
        registrationService.registration(request)
        val userDetails = userDetailsService.loadUserByUsername(request.email)

        val accessToken = createAccessToken(userDetails)
        val refreshToken = createRefreshToken(userDetails)
        refreshTokenRepository.save(refreshToken, userDetails)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )
        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        refreshTokenRepository.save(refreshToken, user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun authentication(accessToken: String): AuthenticationResponse {
        tokenService.extractEmail(accessToken)?.let { email ->
            val tokenUserDetails = userDetailsService.loadUserByUsername(email)
            if (!tokenService.isExpired(accessToken)) {
                val newAccessToken = createAccessToken(tokenUserDetails)
                val refreshToken = createRefreshToken(tokenUserDetails)
                refreshTokenRepository.save(refreshToken, tokenUserDetails)
                return AuthenticationResponse(
                    accessToken = newAccessToken,
                    refreshToken = refreshToken
                )
            }
        }

        throw AccessDeniedException("Invalid creds")
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedEmail = tokenService.extractEmail(refreshToken)
        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)
            if (!tokenService.isExpired(refreshToken) && refreshTokenUserDetails?.username == currentUserDetails.username)
                createAccessToken(currentUserDetails)
            else
                null
        }
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}
