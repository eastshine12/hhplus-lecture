package com.hhplus.lecture.infrastructure.repository.lecture

import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRegistration
import com.hhplus.lecture.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LectureRegistrationJpaRepository : JpaRepository<LectureRegistration, Long> {
    fun countByLectureId(lectureId: Long): Long
    fun findByUser(user: User): List<LectureRegistration>
    @Query("""
        SELECT lr.lecture
        FROM LectureRegistration lr
        WHERE lr.user = :user
    """)
    fun findLecturesByUser(@Param("user") user: User): List<Lecture>
    fun findByUserIdAndLectureId(userId: Long, lectureId: Long): LectureRegistration?
}
