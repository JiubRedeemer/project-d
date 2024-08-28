package com.jiubredeemer.dal.service

import com.jiubredeemer.common.exceptions.NotFoundException
import com.jiubredeemer.dal.converter.RoomConverter
import com.jiubredeemer.dal.entities.Room
import com.jiubredeemer.dal.entities.RoomUser
import com.jiubredeemer.dal.entities.RoomsUserKey
import com.jiubredeemer.dal.entities.User
import com.jiubredeemer.dal.models.RoomDto
import com.jiubredeemer.dal.repository.RoomRepository
import com.jiubredeemer.dal.repository.RoomsUserRepository
import com.jiubredeemer.dal.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Сервис для управления комнатами.
 *
 * @property roomRepository репозиторий комнат
 * @property userRepository репозиторий пользователей
 * @property roomsUserRepository репозиторий пользователей комнат
 * @property roomConverter конвертер комнат
 */
@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val roomsUserRepository: RoomsUserRepository,
    private val roomConverter: RoomConverter
) {

    /**
     * Получить комнату по её идентификатору.
     *
     * @param roomId идентификатор комнаты
     * @return DTO комнаты
     * @throws NotFoundException если комната не найдена
     */
    @Transactional
    fun readById(roomId: UUID): RoomDto {
        return roomRepository.findById(roomId).map { room -> roomConverter.toDto(room) }
            .orElseThrow { NotFoundException("Room not found") }
    }

    /**
     * Получить все комнаты для заданного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список DTO комнат
     */
    @Transactional
    fun readByUserId(userId: UUID): List<RoomDto> {
        return roomRepository.findAllByUserId(userId).map { room -> roomConverter.toDto(room) }
    }

    /**
     * Создать новую комнату.
     *
     * @param roomDto DTO комнаты
     * @param userId идентификатор пользователя, создающего комнату
     * @return созданная сущность комнаты
     * @throws NotFoundException если пользователь не найден
     */
    @Transactional
    fun create(roomDto: RoomDto, userId: UUID): Room {
        val roomToCreate = roomConverter.toEntity(roomDto)
        val roomOwner = userRepository.findById(userId).orElse(null)
            ?: throw NotFoundException("User not found for creating room")
        roomToCreate.owner = roomOwner
        val createdRoom = roomRepository.save<Room>(roomToCreate)
        addRoomUser(roomOwner, createdRoom, listOf(RoomUser.Role.MASTER))
        return createdRoom
    }

    /**
     * Удалить комнату по её идентификатору.
     *
     * @param roomId идентификатор комнаты
     * @return true, если комната была удалена
     */
    @Transactional
    fun delete(roomId: UUID): Boolean {
        roomRepository.deleteById(roomId)
        return true
    }

    /**
     * Добавить пользователя в комнату с заданными ролями.
     *
     * @param roomId идентификатор комнаты
     * @param userId идентификатор пользователя
     * @param roles роли, которые нужно назначить пользователю в комнате
     * @return обновленная сущность комнаты
     * @throws NotFoundException если комната или пользователь не найдены
     */
    @Transactional
    fun addUser(roomId: UUID, userId: UUID, roles: List<RoomUser.Role>): Room {
        val room = roomRepository.findById(roomId).orElse(null)
            ?: throw NotFoundException("Room not found for add to room")
        val user = userRepository.findById(userId).orElse(null)
            ?: throw NotFoundException("User not found for add to room")
        addRoomUser(user, room, roles)
        return room
    }

    /**
     * Удалить пользователя из комнаты.
     *
     * @param roomId идентификатор комнаты
     * @param userId идентификатор пользователя
     * @return true, если пользователь был удален
     * @throws NotFoundException если пользователь в комнате не найден
     */
    @Transactional
    fun removeUser(roomId: UUID, userId: UUID): Boolean {
        roomsUserRepository.findByRoomAndUser(roomId, userId)?.let { roomUser ->
            roomsUserRepository.delete(roomUser)
        } ?: throw NotFoundException("User in room not found")
        return true
    }

    private fun addRoomUser(user: User, createdRoom: Room, roles: List<RoomUser.Role>) {
        val userRoomToCreate = RoomUser()
        userRoomToCreate.user = user
        userRoomToCreate.room = createdRoom
        val key = RoomsUserKey()
        key.roomId = createdRoom.id
        key.userId = user.id
        userRoomToCreate.id = key
        userRoomToCreate.roles = roles
        roomsUserRepository.save(userRoomToCreate)
    }
}
