package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.common.exception.MaxSeatsReachedException
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class Lecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var lecturerName: String,
    var date: LocalDate,
    var registeredCount: Int,
) {
    companion object {
        const val MAX_SEATS: Int = 30
    }

    fun incrementRegisteredCount(): Lecture {
        if (registeredCount < MAX_SEATS) {
            this.registeredCount += 1
            return this
        } else {
            throw MaxSeatsReachedException("강의의 최대 수용 인원이 초과되었습니다.")
        }
    }
}
