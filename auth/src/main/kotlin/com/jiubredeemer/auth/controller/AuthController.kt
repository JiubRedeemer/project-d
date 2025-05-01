package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.model.request.AuthenticationRequest
import com.jiubredeemer.auth.model.request.RefreshTokenRequest
import com.jiubredeemer.auth.model.request.UserRegistration
import com.jiubredeemer.auth.model.response.AuthenticationResponse
import com.jiubredeemer.auth.model.response.TokenResponse
import com.jiubredeemer.auth.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Регистрация и авторизация",
    description = "Методы для регистрации, авторизации и обновления токена по паролю/токену",
)
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @Operation(summary = "Аутентификация по учетным данным")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Успешная аутентификация",
                content = [Content(schema = Schema(implementation = AuthenticationResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Неверные учетные данные",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping
    fun authenticateByCreds(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
        authenticationService.authentication(authRequest)

    @Operation(summary = "Аутентификация по токену")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Успешная аутентификация",
                content = [Content(schema = Schema(implementation = AuthenticationResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Неверный токен",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @GetMapping
    fun authenticateByToken(@RequestHeader("X-Auth-Token") accessToken: String): AuthenticationResponse =
        authenticationService.authentication(accessToken)

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Пользователь успешно зарегистрирован",
                content = [Content(schema = Schema(implementation = AuthenticationResponse::class))]
            ),
            ApiResponse(
                responseCode = "422", description = "Некорректные данные запроса",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/registration")
    fun registration(@RequestBody registrationRequest: UserRegistration): AuthenticationResponse =
        authenticationService.registration(registrationRequest)

    @Operation(summary = "Обновление access токена по refresh токену")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Токен успешно обновлен",
                content = [Content(schema = Schema(implementation = TokenResponse::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Неверный refresh токен",
                content = [Content(schema = Schema())]
            )
        ]
    )
    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshTokenRequest): TokenResponse =
        authenticationService.refreshAccessToken(request.token)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")

    private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(
            token = this
        )
}
