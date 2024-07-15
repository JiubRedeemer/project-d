package com.jiubredeemer.auth.exceptions

class ValidationException() : RuntimeException() {
    private var fieldName: String = ""
    private var code: ErrorCode = ErrorCode.OTHER

    constructor(code: ErrorCode) : this() {
        this.code = code;
    }

    constructor(code: ErrorCode, fieldName: String) : this() {
        this.code = code;
        this.fieldName = fieldName
    }
}
