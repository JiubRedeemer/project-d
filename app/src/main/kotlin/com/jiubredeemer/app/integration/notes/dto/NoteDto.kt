package com.jiubredeemer.app.integration.notes.dto

import java.util.*


data class NoteDto(
    val id: UUID? = null,
    val notebookId: UUID? = null,
    val name: String? = null,
    val noteText: String? = null,
    val tags: List<TagDto>? = null
)
