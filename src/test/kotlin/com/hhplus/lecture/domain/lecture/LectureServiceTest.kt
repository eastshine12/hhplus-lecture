package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.application.lecture.dto.LectureRegisterRequest
import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.infrastructure.repository.lecture.LectureRegistrationRepository
import com.hhplus.lecture.infrastructure.repository.lecture.LectureRepository
import com.hhplus.lecture.infrastructure.repository.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class LectureServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var lectureRepository: LectureRepository
    private lateinit var lectureRegistrationRepository: LectureRegistrationRepository
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        lectureRepository = mockk()
        lectureRegistrationRepository = mockk()
        lectureService = LectureService(userRepository, lectureRepository, lectureRegistrationRepository)
    }

    @Test
    fun `사용자는 특강을 신청할 수 있어야 한다`() {
        // given
        val userId = 1L
        val lectureId = 1L
        val user = User(id = userId, name = "홍길동", email = "hong@example.com")
        val lecture = Lecture(id = lectureId, title = "코틀린 강의", lecturerName = "김철수", date = LocalDate.now())
        val request = LectureRegisterRequest(userId = userId, lectureId = lectureId)
        val lectureRegistration = LectureRegistration(user = user, lecture = lecture)

        every { userRepository.findById(userId) } returns user
        every { lectureRepository.findById(lectureId) } returns lecture
        every { lectureRegistrationRepository.save(any()) } returns lectureRegistration

        // when
        val result: LectureRegistration = lectureService.register(request)

        // then
        assertEquals(userId, result.user.id)
        assertEquals(lectureId, result.lecture.id)
    }
}
