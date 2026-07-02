package com.jiubredeemer.dal.service

import com.jiubredeemer.dal.entity.Room
import com.jiubredeemer.dal.entity.RoomSchedule
import com.jiubredeemer.dal.repository.RoomScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.*

@Service
class RoomScheduleService(
    private val roomScheduleRepository: RoomScheduleRepository,
) {
    @Transactional(readOnly = true)
    fun findByRoomId(roomId: UUID): RoomSchedule? =
        roomScheduleRepository.findByRoomId(roomId)

    @Transactional(readOnly = true)
    fun findByRoomIds(roomIds: List<UUID>): Map<UUID, RoomSchedule> =
        roomScheduleRepository.findByRoomIds(roomIds)
            .associateBy { it.room!!.id!! }

    @Transactional
    fun save(schedule: RoomSchedule): RoomSchedule =
        roomScheduleRepository.save(schedule)

    @Transactional
    fun deleteByRoomId(roomId: UUID) {
        roomScheduleRepository.findByRoomId(roomId)?.let { roomScheduleRepository.delete(it) }
    }

    @Transactional
    fun upsert(room: Room, isRecurring: Boolean, sessionDatetime: LocalDateTime?,
               recurrenceType: String?, dayOfWeek: Int?, dayOfMonth: Int?,
               sessionTime: java.time.LocalTime?): RoomSchedule {
        val schedule = roomScheduleRepository.findByRoomId(room.id!!) ?: RoomSchedule().also { it.room = room }
        schedule.isRecurring = isRecurring
        schedule.sessionDatetime = sessionDatetime
        schedule.recurrenceType = recurrenceType
        schedule.dayOfWeek = dayOfWeek
        schedule.dayOfMonth = dayOfMonth
        schedule.sessionTime = sessionTime
        return roomScheduleRepository.save(schedule)
    }

    fun computeNextSessionAt(schedule: RoomSchedule): LocalDateTime? {
        if (!schedule.isRecurring) {
            return schedule.sessionDatetime?.takeIf { it.isAfter(LocalDateTime.now()) }
        }
        val time = schedule.sessionTime ?: return null
        val now = LocalDateTime.now()
        return when (schedule.recurrenceType) {
            "WEEKLY", "BIWEEKLY" -> {
                val dow = DayOfWeek.of(schedule.dayOfWeek ?: return null)
                val intervalDays = if (schedule.recurrenceType == "BIWEEKLY") 14L else 7L
                var next = LocalDate.now().with(TemporalAdjusters.nextOrSame(dow)).atTime(time)
                if (!next.isAfter(now)) next = next.plusDays(intervalDays)
                next
            }
            "MONTHLY" -> {
                val dom = schedule.dayOfMonth ?: return null
                var next = try { LocalDate.now().withDayOfMonth(dom).atTime(time) } catch (_: Exception) { return null }
                if (!next.isAfter(now)) next = next.plusMonths(1)
                next
            }
            else -> null
        }
    }
}
