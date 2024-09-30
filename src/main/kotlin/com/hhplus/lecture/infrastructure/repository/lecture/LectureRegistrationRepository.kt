package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.LectureRegistration

interface LectureRegistrationRepository {
    fun save(lectureRegistration: LectureRegistration): LectureRegistration
}
