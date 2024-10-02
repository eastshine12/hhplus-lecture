package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.common.exception.MaxSeatsReachedException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class LectureTest {

    @Test
    fun `정상적으로 수강 인원을 증가시켜야 한다`() {
        // given
        val lecture = Lecture(
            id = 1L,
            title = "강의1",
            lecturerName = "강사1",
            date = LocalDate.now(),
            registeredCount = 0
        )

        // when
        lecture.incrementRegisteredCount()

        // then
        assertEquals(1, lecture.registeredCount)
    }

    @Test
    fun `최대 수용 인원이 초과되면 예외가 발생해야 한다`() {
        // given
        val lecture = Lecture(
            id = 1L,
            title = "강의1",
            lecturerName = "강사1",
            date = LocalDate.now(),
            registeredCount = Lecture.MAX_SEATS
        )

        // when, then
        val exception = assertThrows<MaxSeatsReachedException> {
            lecture.incrementRegisteredCount()
        }

        assertEquals("강의의 최대 수용 인원이 초과되었습니다.", exception.message)
    }
}
