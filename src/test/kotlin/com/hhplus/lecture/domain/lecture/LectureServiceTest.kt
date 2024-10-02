package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.common.exception.LectureNotFoundException
import com.hhplus.lecture.domain.user.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import kotlin.test.assertTrue

class LectureServiceTest {
    private lateinit var lectureRepository: LectureRepository
    private lateinit var lectureRegistrationRepository: LectureRegistrationRepository
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setUp() {
        lectureRepository = mockk()
        lectureRegistrationRepository = mockk()
        lectureService = LectureService(lectureRepository, lectureRegistrationRepository)
    }

    @Test
    fun `존재하지 않는 강의ID를 조회하면 예외가 발생해야 한다`() {
        // given
        val lectureId = 1L
        every { lectureRepository.findByIdOrNull(lectureId) } returns null

        // when, then
        val exception = assertThrows<LectureNotFoundException> {
            lectureService.findById(lectureId)
        }
        assertEquals("강의 정보를 찾을 수 없습니다. ID: $lectureId", exception.message)
    }

    @Test
    fun `사용자는 특강을 신청할 수 있어야 한다`() {
        // given
        val userId = 1L
        val lectureId = 1L
        val user = User(id = userId, name = "홍길동", email = "hong@example.com")
        val lecture = Lecture(id = lectureId, title = "코틀린 강의", lecturerName = "강사1", date = LocalDate.now(), registeredCount = 0)
        val lectureRegistration = LectureRegistration(user = user, lecture = lecture)

        every { lectureRegistrationRepository.save(any()) } returns lectureRegistration
        every { lectureRepository.findByIdOrNull(lecture.id!!) } returns lecture

        // when
        val result: LectureRegistration = lectureService.register(user, lectureId)

        // then
        assertEquals(userId, result.user.id)
        assertEquals(lectureId, result.lecture.id)
        assertTrue(lecture.registeredCount == 1)
    }

    @Test
    fun `날짜 별로 현재 신청 가능한 특강 목록을 조회할 수 있어야 한다`() {
        // given
        val date = LocalDate.of(2024, 10, 1)
        val lecture1 = Lecture(id = 1L, title = "코틀린 강의", lecturerName = "강사1", date = date, registeredCount = 0)
        val lecture2 = Lecture(id = 2L, title = "자바 강의", lecturerName = "강사2", date = date, registeredCount = 0)
        val lectures = listOf(lecture1, lecture2)

        every { lectureRepository.findAvailableLecturesByDate(date, Lecture.MAX_SEATS) } returns lectures

        // when
        val result = lectureService.getAvailableLecturesByDate(date)

        // then
        assertEquals(lectures, result)
        verify { lectureRepository.findAvailableLecturesByDate(date, Lecture.MAX_SEATS) }
    }

    @Test
    fun `사용자는 자신이 신청 완료한 특강 목록을 조회할 수 있어야 한다`() {
        // given
        val user = User(id = 1L, name = "홍길동", email = "hong@example.com")
        val lecture1 = Lecture(id = 1L, title = "코틀린 강의", lecturerName = "강사1", date = LocalDate.now(), registeredCount = 0)
        val lecture2 = Lecture(id = 2L, title = "자바 강의", lecturerName = "강사2", date = LocalDate.now(), registeredCount = 1)
        val registeredLectures = listOf(lecture1, lecture2)

        every { lectureRegistrationRepository.findLecturesByUser(user) } returns registeredLectures

        // when
        val result = lectureService.getLecturesByUserId(user)

        // then
        assertEquals(registeredLectures, result)
        verify { lectureRegistrationRepository.findLecturesByUser(user) }
    }
}
