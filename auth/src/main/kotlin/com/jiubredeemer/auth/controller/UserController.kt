package com.jiubredeemer.auth.controller

import com.jiubredeemer.auth.annotation.HasRoleOrThrow
import com.jiubredeemer.auth.model.request.ChangeEmailRequest
import com.jiubredeemer.auth.model.request.SendChangeEmailCodeRequest
import com.jiubredeemer.auth.model.response.UserInfoResponse
import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.auth.service.ChangeEmailService
import com.jiubredeemer.dal.model.UserDto
import com.jiubredeemer.dal.repository.RoomsUserRepository
import com.jiubredeemer.dal.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val accessChecker: AccessChecker,
    private val roomsUserRepository: RoomsUserRepository,
    private val changeEmailService: ChangeEmailService,
) {

    private val sharedRoomId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    @HasRoleOrThrow("ADMIN")
    @GetMapping
    fun getAll(): List<UserDto> {
        return userService.getAllUsers()
    }

    @HasRoleOrThrow("ADMIN", "USER")
    @GetMapping("/myId")
    fun getMe(): UUID? {
        return accessChecker.getCurrentUser().id
    }

    @HasRoleOrThrow("ADMIN", "USER")
    @GetMapping("/me")
    fun getCurrentUserInfo(): UserInfoResponse {
        val user = accessChecker.getCurrentUser()
        return UserInfoResponse(
            username = user.username,
            email = user.email,
            registrationDate = user.registrationDate,
            lastActivity = user.lastActivity,
        )
    }

    @GetMapping("/check")
    fun checkToken(@RequestParam("roomId") roomId: UUID, @RequestParam("userId") userId: UUID): Boolean {
        val currentUserId = accessChecker.getCurrentUser().id ?: return false
        if (currentUserId != userId) {
            return false
        }

        if (roomId == sharedRoomId) {
            return true
        }

        return roomsUserRepository.findByRoomAndUser(roomId, userId) != null
    }

    @HasRoleOrThrow("ADMIN", "USER")
    @PostMapping("/email/send-verification-code")
    fun sendEmailChangeVerificationCode(@RequestBody request: SendChangeEmailCodeRequest): ResponseEntity<Unit> {
        changeEmailService.sendVerificationCode(request.newEmail)
        return ResponseEntity.ok().build()
    }

    @HasRoleOrThrow("ADMIN", "USER")
    @PostMapping("/email/change")
    fun changeEmail(@RequestBody request: ChangeEmailRequest): ResponseEntity<Unit> {
        changeEmailService.changeEmail(request)
        return ResponseEntity.ok().build()
    }
}
