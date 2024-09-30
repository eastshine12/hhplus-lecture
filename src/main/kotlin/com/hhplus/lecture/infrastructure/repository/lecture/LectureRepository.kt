package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture

interface LectureRepository {
    fun findById(lectureId: Long): Lecture?
}
