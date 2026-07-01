package com.jiubredeemer.dal.repository

import com.jiubredeemer.dal.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : JpaRepository<Room, UUID> {

    @Query("select r from Room r inner join r.roomUsers roomUsers where roomUsers.user.id = :userId order by roomUsers.createDatetime desc")
    fun findAllByUserId(@Param("userId") userId: UUID): List<Room>


    @Query("select r from Room r where r.id = :id and r.owner.id = :ownerId")
    fun findByIdAndOwnerId(@Param("id") id: UUID, @Param("ownerId") ownerId: UUID): Room?

    @Query("select r from Room r where r.name = :name and r.owner.id = :ownerId")
    fun findByNameAndOwnerId(@Param("name") name: String, @Param("ownerId") ownerId: UUID): Optional<Room>

    @Query("""
        select r, (select count(ru) from RoomUser ru where ru.room.id = r.id) as memberCount
        from Room r
        where r.isPublic = true
          and r.deleteDatetime is null
          and (cast(:search as string) is null or lower(r.name) like lower(concat('%', cast(:search as string), '%')))
          and not exists (select ru from RoomUser ru where ru.room.id = r.id and ru.user.id = :userId)
        order by r.lastActivityDatetime desc
    """)
    fun findPublicRoomsExcludingMember(
        @Param("userId") userId: UUID,
        @Param("search") search: String?
    ): List<Array<Any>>
}
