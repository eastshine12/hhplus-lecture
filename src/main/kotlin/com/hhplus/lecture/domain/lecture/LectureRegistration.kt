package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "lecture_id"])]
)
@Entity
class LectureRegistration(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    var lecture: Lecture,

    var registeredAt: LocalDateTime = LocalDateTime.now(),
)
