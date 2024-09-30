package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class LectureRepositoryJpaImpl(
    private val lectureRepository: LectureJpaRepository,
) : LectureRepository {
    override fun findById(lectureId: Long): Lecture? {
        return lectureRepository.findByIdOrNull(lectureId)
    }
}
