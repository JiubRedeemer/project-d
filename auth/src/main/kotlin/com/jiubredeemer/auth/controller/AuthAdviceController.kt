package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.exceptions.ValidationException
import com.jiubredeemer.auth.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthAdviceController {
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponse> {

        val errorMessage = ErrorResponse(
            ex.code.name, ex.code.name + " field(s):" + ex.fieldName
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_ACCEPTABLE)
    }
}
