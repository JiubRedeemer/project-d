package com.jiubredeemer.auth.exceptions

class ValidationException() : RuntimeException() {
    override var message: String? = null
    var fieldName: String = ""
        private set
    var code: ErrorCode = ErrorCode.OTHER
        private set

    constructor(code: ErrorCode) : this() {
        this.message = code.name
        this.code = code
    }

    constructor(code: ErrorCode, fieldName: String) : this() {
        this.message = code.name
        this.code = code
        this.fieldName = fieldName
    }
}
