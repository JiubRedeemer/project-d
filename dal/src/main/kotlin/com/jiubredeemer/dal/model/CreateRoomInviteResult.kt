package com.jiubredeemer.dal.model

data class CreateRoomInviteResult(
    val emailDispatchNeeded: Boolean,
    val inviteToken: String?,
    val invitedEmail: String,
    val roomName: String,
)
