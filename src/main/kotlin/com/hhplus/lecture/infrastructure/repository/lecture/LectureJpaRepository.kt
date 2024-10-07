package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface LectureJpaRepository : JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l WHERE l.date = :date AND l.registeredCount < :maxSeats")
    fun findAvailableLecturesByDate(date: LocalDate, maxSeats: Int): List<Lecture>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id= :lectureId")
    fun findByIdOrNullWithLock(lectureId: Long): Lecture?
}
