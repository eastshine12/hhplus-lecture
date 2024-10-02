package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.User

interface LectureRegistrationRepository {
    fun save(lectureRegistration: LectureRegistration): LectureRegistration

    fun countByLectureId(lectureId: Long): Long

    fun findByUser(user: User): List<LectureRegistration>

    fun findLecturesByUser(user: User): List<Lecture>
}
