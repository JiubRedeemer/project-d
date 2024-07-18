package com.jiubredeemer.auth.exceptions

enum class ErrorCode {
    PASSWORD_DO_NOT_MATCH,
    MISSED_REQUIRED_FIELD,
    OTHER,
    USER_EXISTS_BY_EMAIL,
    USER_EXISTS_BY_USERNAME
}
