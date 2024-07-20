package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.exceptions.ErrorCode
import com.jiubredeemer.auth.exceptions.ValidationException
import com.jiubredeemer.auth.model.response.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AuthAdviceController {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponse> {

        val errorMessage = ErrorResponse(
            ex.code.name, ex.code.name + " field(s):" + ex.fieldName
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwt(ex: ExpiredJwtException): ResponseEntity<ErrorResponse> {

        val errorMessage = ErrorResponse(
            ErrorCode.EXPIRED_JWT.name, "Session expired"
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }
}
