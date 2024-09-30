package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.LectureRegistration
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRegistrationJpaRepository : JpaRepository<LectureRegistration, Long>
