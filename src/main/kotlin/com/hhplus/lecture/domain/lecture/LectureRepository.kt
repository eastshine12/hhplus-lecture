package com.hhplus.lecture.domain.lecture

import java.time.LocalDate

interface LectureRepository {
    fun save(lecture: Lecture): Lecture

    fun findByIdOrNull(lectureId: Long): Lecture?

    fun findByIdOrNullWithLock(lectureId: Long): Lecture?

    fun findAvailableLecturesByDate(date: LocalDate, maxSeats: Int): List<Lecture>

    fun findAllById(ids: List<Long>): List<Lecture>
}
