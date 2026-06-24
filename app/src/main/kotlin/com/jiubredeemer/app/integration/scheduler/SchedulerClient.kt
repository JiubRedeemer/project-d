package com.jiubredeemer.app.integration.scheduler

import com.jiubredeemer.app.integration.configuration.SchedulerProperty
import com.jiubredeemer.app.integration.scheduler.dto.*
import com.jiubredeemer.common.exception.IntegrationAccessException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class SchedulerClient(
    private val restClient: RestClient,
    private val schedulerProperty: SchedulerProperty,
) {
    private val jsonHeaders = HttpHeaders().apply { set("Content-Type", "application/json") }
    private val apiPathSegments: Array<String> =
        schedulerProperty.apiUrl.split("/").filter { it.isNotBlank() }.toTypedArray()

    private fun baseUriBuilder(): UriComponentsBuilder {
        val builder = UriComponentsBuilder.fromHttpUrl(schedulerProperty.baseUrl)
        if (apiPathSegments.isNotEmpty()) {
            builder.pathSegment(*apiPathSegments)
        }
        return builder
    }

    private fun authorizedHeaders(userId: UUID, roles: String?): HttpHeaders {
        return HttpHeaders().apply {
            addAll(jsonHeaders)
            set("X-User-Id", userId.toString())
            roles?.let { set("X-User-Roles", it) }
        }
    }

    fun createGame(request: CreateGameRequest, userId: UUID, roles: String?): GameResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .body(request)
                .retrieve()
                .toEntity(GameResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on createGame, cause: ${e.message}")
        }
    }

    fun listGames(from: String?, to: String?): List<GameResponse>? {
        return try {
            val builder = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
            from?.let { builder.queryParam("from", it) }
            to?.let { builder.queryParam("to", it) }
            val uri = builder.toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(jsonHeaders) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<GameResponse>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on listGames, cause: ${e.message}")
        }
    }

    fun getGame(gameId: UUID): GameResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(jsonHeaders) }
                .retrieve()
                .toEntity(GameResponse::class.java)
                .body
        } catch (_: HttpClientErrorException.NotFound) {
            null
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on getGame, cause: ${e.message}")
        }
    }

    fun updateGame(gameId: UUID, request: UpdateGameRequest, userId: UUID, roles: String?): GameResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .toUriString()
            restClient.patch()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .body(request)
                .retrieve()
                .toEntity(GameResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on updateGame, cause: ${e.message}")
        }
    }

    fun rescheduleGame(gameId: UUID, request: RescheduleGameRequest, userId: UUID, roles: String?): GameResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.rescheduleUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .body(request)
                .retrieve()
                .toEntity(GameResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on rescheduleGame, cause: ${e.message}")
        }
    }

    fun cancelGame(gameId: UUID, userId: UUID, roles: String?): GameResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.cancelUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .retrieve()
                .toEntity(GameResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on cancelGame, cause: ${e.message}")
        }
    }

    fun invitePlayers(gameId: UUID, request: InvitePlayersRequest, userId: UUID, roles: String?): List<InvitationResponse>? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.invitationsUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .body(request)
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<InvitationResponse>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on invitePlayers, cause: ${e.message}")
        }
    }

    fun listInvitations(gameId: UUID): List<InvitationResponse>? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.invitationsUrl)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(jsonHeaders) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<InvitationResponse>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on listInvitations, cause: ${e.message}")
        }
    }

    fun acceptInvitation(invitationId: UUID, request: InvitationReplyRequest?, userId: UUID, roles: String?): InvitationResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.invitationsUrl)
                .pathSegment(invitationId.toString())
                .pathSegment(schedulerProperty.acceptUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .apply {
                    if (request != null) body(request)
                }
                .retrieve()
                .toEntity(InvitationResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on acceptInvitation, cause: ${e.message}")
        }
    }

    fun declineInvitation(invitationId: UUID, request: InvitationReplyRequest?, userId: UUID, roles: String?): InvitationResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.invitationsUrl)
                .pathSegment(invitationId.toString())
                .pathSegment(schedulerProperty.declineUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .apply {
                    if (request != null) body(request)
                }
                .retrieve()
                .toEntity(InvitationResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on declineInvitation, cause: ${e.message}")
        }
    }

    fun setAttendance(gameId: UUID, request: AttendanceRequest, userId: UUID, roles: String?): AttendanceResponse? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.attendanceUrl)
                .toUriString()
            restClient.post()
                .uri(uri)
                .headers { it.addAll(authorizedHeaders(userId, roles)) }
                .body(request)
                .retrieve()
                .toEntity(AttendanceResponse::class.java)
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on setAttendance, cause: ${e.message}")
        }
    }

    fun listAttendance(gameId: UUID): List<AttendanceResponse>? {
        return try {
            val uri = baseUriBuilder()
                .pathSegment(schedulerProperty.gamesUrl)
                .pathSegment(gameId.toString())
                .pathSegment(schedulerProperty.attendanceUrl)
                .toUriString()
            restClient.get()
                .uri(uri)
                .headers { it.addAll(jsonHeaders) }
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<AttendanceResponse>>() {})
                .body
        } catch (e: Exception) {
            throw IntegrationAccessException("Scheduler don't response on listAttendance, cause: ${e.message}")
        }
    }
}
