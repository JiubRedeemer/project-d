package com.jiubredeemer.app.integration.magic.dto

data class ImportResult(
    val total: Int,
    val imported: Int,
    val updated: Int,
    val failed: Int
)
