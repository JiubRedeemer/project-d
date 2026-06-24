package com.jiubredeemer.app.integration.scheduler.dto

data class ErrorResponse(
    val timestamp: String? = null,
    val error: String? = null,
    val message: String? = null,
)

enum class GameStatus {
    PLANNED,
    RESCHEDULED,
    CANCELLED,
    COMPLETED,
}

enum class InvitationStatus {
    PENDING,
    ACCEPTED,
    DECLINED,
}

enum class AttendanceStatus {
    GOING,
    MAYBE,
    NOT_GOING,
}

data class CreateGameRequest(
    val campaignId: String,
    val title: String,
    val description: String? = null,
    val scheduledAt: String,
    val durationMinutes: Int,
    val timezone: String,
    val location: String,
    val playerLimit: Int,
)

data class UpdateGameRequest(
    val title: String? = null,
    val description: String? = null,
    val scheduledAt: String? = null,
    val durationMinutes: Int? = null,
    val timezone: String? = null,
    val location: String? = null,
    val playerLimit: Int? = null,
)

data class RescheduleGameRequest(
    val scheduledAt: String,
    val reason: String? = null,
)

data class GameResponse(
    val id: String? = null,
    val campaignId: String? = null,
    val gmId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val scheduledAt: String? = null,
    val durationMinutes: Int? = null,
    val timezone: String? = null,
    val location: String? = null,
    val status: GameStatus? = null,
    val playerLimit: Int? = null,
)

data class InvitePlayersRequest(
    val playerIds: List<String>,
)

data class InvitationReplyRequest(
    val comment: String? = null,
)

data class InvitationResponse(
    val id: String? = null,
    val gameId: String? = null,
    val playerId: String? = null,
    val status: InvitationStatus? = null,
    val respondedAt: String? = null,
    val comment: String? = null,
)

data class AttendanceRequest(
    val status: AttendanceStatus,
)

data class AttendanceResponse(
    val id: String? = null,
    val gameId: String? = null,
    val playerId: String? = null,
    val status: AttendanceStatus? = null,
)
