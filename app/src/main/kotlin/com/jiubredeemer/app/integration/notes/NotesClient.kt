package com.jiubredeemer.app.integration.notes

import com.jiubredeemer.app.integration.configuration.NotesProperty
import com.jiubredeemer.app.integration.notes.dto.NoteDto
import com.jiubredeemer.app.integration.notes.dto.NotebookDto
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class NotesClient(
    private val notesProperty: NotesProperty,
    private val restClient: RestClient
) {
    private val headers = HttpHeaders().apply { set("Content-Type", "application/json") }

    fun getNotebook(roomId: UUID, characterId: UUID): NotebookDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(notesProperty.baseUrl)
                .pathSegment(notesProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(notesProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(notesProperty.notesUrl)
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(NotebookDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Notes don't response on getNotebook, cause: ${e.message}")
        }
    }

    fun getNoteById(roomId: UUID, characterId: UUID, id: UUID): NoteDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(notesProperty.baseUrl)
                .pathSegment(notesProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(notesProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(notesProperty.notesUrl)
                .pathSegment(id.toString())
                .toUriString()
            val response = restClient.get()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
                .toEntity(NoteDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Notes don't response on getNoteById, cause: ${e.message}")
        }
    }

    fun updateNote(roomId: UUID, characterId: UUID, id: UUID, noteDto: NoteDto): NoteDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(notesProperty.baseUrl)
                .pathSegment(notesProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(notesProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(notesProperty.notesUrl)
                .pathSegment(id.toString())
                .toUriString()
            val response = restClient.patch()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(noteDto)
                .retrieve()
                .toEntity(NoteDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Notes don't response on updateNote, cause: ${e.message}")
        }
    }

    fun addNote(roomId: UUID, characterId: UUID, noteDto: NoteDto): NoteDto? {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(notesProperty.baseUrl)
                .pathSegment(notesProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(notesProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(notesProperty.notesUrl)
                .toUriString()
            val response = restClient.put()
                .uri(uri)
                .headers { it.addAll(headers) }
                .body(noteDto)
                .retrieve()
                .toEntity(NoteDto::class.java)
            return response.body
        } catch (e: Exception) {
            throw IntegrationAccessException("Notes don't response on addNote, cause: ${e.message}")
        }
    }

    fun deleteNote(roomId: UUID, characterId: UUID, id: UUID) {
        try {
            val uri = UriComponentsBuilder
                .fromHttpUrl(notesProperty.baseUrl)
                .pathSegment(notesProperty.apiUrl)
                .pathSegment(roomId.toString())
                .pathSegment(notesProperty.charactersUrl)
                .pathSegment(characterId.toString())
                .pathSegment(notesProperty.notesUrl)
                .pathSegment(id.toString())
                .toUriString()
            restClient.delete()
                .uri(uri)
                .headers { it.addAll(headers) }
                .retrieve()
        } catch (e: Exception) {
            throw IntegrationAccessException("Notes don't response on deleteNote, cause: ${e.message}")
        }
    }

}