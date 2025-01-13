package com.jiubredeemer.auth.exception

enum class ErrorCode {
    PASSWORD_DO_NOT_MATCH,
    MISSED_REQUIRED_FIELD,
    OTHER,
    USER_EXISTS_BY_EMAIL,
    USER_EXISTS_BY_USERNAME,
    EXPIRED_JWT
}
