package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRegistration
import com.hhplus.lecture.domain.lecture.LectureRegistrationRepository
import com.hhplus.lecture.domain.user.User
import org.springframework.stereotype.Repository

@Repository
class LectureRegistrationRepositoryJpaImpl(
    private val repository: LectureRegistrationJpaRepository,
) : LectureRegistrationRepository {
    override fun save(lectureRegistration: LectureRegistration): LectureRegistration {
        return repository.save(lectureRegistration)
    }
    override fun countByLectureId(lectureId: Long): Long {
        return repository.countByLectureId(lectureId)
    }

    override fun findByUser(user: User): List<LectureRegistration> {
        return repository.findByUser(user)
    }

    override fun findLecturesByUser(user: User): List<Lecture> {
        return repository.findLecturesByUser(user)
    }
}
