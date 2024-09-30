package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import org.springframework.data.jpa.repository.JpaRepository

interface LectureJpaRepository : JpaRepository<Lecture, Long>
