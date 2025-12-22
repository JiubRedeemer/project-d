package com.jiubredeemer.app.integration.notes.dto

import java.util.*


data class NotebookDto(
    val id: UUID? = null,
    val roomId: UUID? = null,
    val characterId: UUID? = null,
    val notes: List<NoteDto?>? = null

)

