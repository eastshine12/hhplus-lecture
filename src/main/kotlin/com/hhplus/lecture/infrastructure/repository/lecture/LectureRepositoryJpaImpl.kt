package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class LectureRepositoryJpaImpl(
    private val lectureRepository: LectureJpaRepository,
) : LectureRepository {
    override fun save(lecture: Lecture): Lecture {
        return lectureRepository.save(lecture)
    }

    override fun findByIdOrNull(lectureId: Long): Lecture? {
        return lectureRepository.findByIdOrNull(lectureId)
    }

    override fun findAvailableLecturesByDate(date: LocalDate, maxSeats: Int): List<Lecture> {
        return lectureRepository.findAvailableLecturesByDate(date, maxSeats)
    }

    override fun findAllById(ids: List<Long>): List<Lecture> {
        return lectureRepository.findAllById(ids)
    }
}
