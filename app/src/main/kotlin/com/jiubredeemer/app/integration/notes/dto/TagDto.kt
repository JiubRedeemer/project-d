package com.jiubredeemer.app.integration.notes.dto

import java.util.*


data class TagDto(
    val id: UUID? = null,
    val notebookId: UUID? = null,
    val name: String? = null,
    val color: String? = null
)
