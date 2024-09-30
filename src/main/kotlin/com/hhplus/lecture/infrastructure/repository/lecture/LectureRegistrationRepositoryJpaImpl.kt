package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.LectureRegistration
import org.springframework.stereotype.Repository

@Repository
class LectureRegistrationRepositoryJpaImpl(
    private val repository: LectureRegistrationJpaRepository,
) : LectureRegistrationRepository {
    override fun save(lectureRegistration: LectureRegistration): LectureRegistration {
        return repository.save(lectureRegistration)
    }
}
