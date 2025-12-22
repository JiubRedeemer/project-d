package com.jiubredeemer.app.notes.note.controller

import com.jiubredeemer.app.integration.notes.dto.NoteDto
import com.jiubredeemer.app.integration.notes.dto.NotebookDto
import com.jiubredeemer.app.notes.note.service.NoteApiService
import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/rooms/{roomId}/characters/{characterId}/notes")
@Tag(name = "Заметки", description = "API для работы с заметками персонажей в комнатах")
class NoteApiController(
    private val noteApiService: NoteApiService
) {

    @Operation(summary = "Получить блокнот персонажа")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Блокнот найден",
                content = [Content(schema = Schema(implementation = NotebookDto::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Блокнот не найден",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content()]
            )
        ]
    )
    @GetMapping("/notebook")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNotebook(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID
    ): NotebookDto {
        return noteApiService.getNotebook(roomId, characterId)
    }

    @Operation(summary = "Получить заметку по ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Заметка найдена",
                content = [Content(schema = Schema(implementation = NoteDto::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Заметка не найдена",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content()]
            )
        ]
    )
    @GetMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun getNoteById(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID
    ): NoteDto {
        return noteApiService.getNoteById(roomId, characterId, id)
    }

    @Operation(summary = "Создать новую заметку")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Заметка успешно создана",
                content = [Content(schema = Schema(implementation = NoteDto::class))]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content()]
            )
        ]
    )
    @PutMapping
    @HasRoleOrThrow("ADMIN", "USER")
    fun addNote(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @RequestBody noteDto: NoteDto
    ): NoteDto {
        return noteApiService.addNote(roomId, characterId, noteDto)
    }

    @Operation(summary = "Обновить заметку по ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Заметка успешно обновлена",
                content = [Content(schema = Schema(implementation = NoteDto::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Заметка не найдена",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content()]
            )
        ]
    )
    @PatchMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun updateNote(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID,
        @RequestBody noteDto: NoteDto
    ): NoteDto {
        return noteApiService.updateNote(roomId, characterId, id, noteDto)
    }

    @Operation(summary = "Удалить заметку по ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "Заметка успешно удалена",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "403", description = "Недостаточно прав",
                content = [Content()]
            )
        ]
    )
    @DeleteMapping("/{id}")
    @HasRoleOrThrow("ADMIN", "USER")
    fun deleteNote(
        @PathVariable roomId: UUID,
        @PathVariable characterId: UUID,
        @PathVariable id: UUID
    ) {
        noteApiService.deleteNote(roomId, characterId, id)
    }
}
