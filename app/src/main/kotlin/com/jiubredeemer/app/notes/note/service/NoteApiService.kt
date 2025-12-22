package com.jiubredeemer.app.notes.note.service

import com.jiubredeemer.app.integration.notes.NotesClient
import com.jiubredeemer.app.integration.notes.dto.NoteDto
import com.jiubredeemer.app.integration.notes.dto.NotebookDto
import com.jiubredeemer.app.room.service.RoomAccessChecker
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.common.exception.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteApiService(
    private val roomAccessChecker: RoomAccessChecker,
    private val accessChecker: AccessChecker,
    private val notesClient: NotesClient
) {
    fun getNotebook(roomId: UUID, characterId: UUID): NotebookDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val notebook = notesClient.getNotebook(roomId, characterId)
        return notebook ?: throw NotFoundException("Notebook for room $roomId and character $characterId not found")
    }

    fun getNoteById(roomId: UUID, characterId: UUID, id: UUID): NoteDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val note = notesClient.getNoteById(roomId, characterId, id)
        return note ?: throw NotFoundException("Note for room $roomId and character $characterId and id $id not found")
    }

    fun updateNote(roomId: UUID, characterId: UUID, id: UUID, noteDto: NoteDto): NoteDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val note = notesClient.updateNote(roomId, characterId, id, noteDto)
        return note ?: throw NotFoundException("Note for room $roomId and character $characterId and id $id not found")
    }

    fun addNote(roomId: UUID, characterId: UUID, noteDto: NoteDto): NoteDto {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        val note = notesClient.addNote(roomId, characterId, noteDto)
        return note ?: throw NotFoundException("Note for room $roomId and character $characterId not found")
    }

    fun deleteNote(roomId: UUID, characterId: UUID, id: UUID) {
        roomAccessChecker.hasAccessOrThrow(roomId, accessChecker.getCurrentUser().id!!)
        notesClient.deleteNote(roomId, characterId, id)
    }
}