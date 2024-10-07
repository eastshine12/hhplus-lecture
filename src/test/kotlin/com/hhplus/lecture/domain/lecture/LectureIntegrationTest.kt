package com.hhplus.lecture.domain.lecture

import com.fasterxml.jackson.databind.ObjectMapper
import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.infrastructure.repository.lecture.LectureJpaRepository
import com.hhplus.lecture.infrastructure.repository.lecture.LectureRegistrationJpaRepository
import com.hhplus.lecture.infrastructure.repository.user.UserJpaRepository
import com.hhplus.lecture.interfaces.api.lecture.dto.req.LectureRegisterRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@SpringBootTest
class LectureIntegrationTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val userRepository: UserJpaRepository,
    private val lectureRepository: LectureJpaRepository,
    private val lectureRegistrationRepository: LectureRegistrationJpaRepository,
) {
    @AfterEach
    fun tearDown() {
        lectureRegistrationRepository.deleteAll()
        lectureRepository.flush()
        userRepository.deleteAll()
        userRepository.flush()
        lectureRepository.deleteAll()
        lectureRepository.flush()
    }

    @Test
    @Order(1)
    fun `동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공해야 한다`() {
        // given
        repeat(40) { index ->
            userRepository.save(
                User(name = "user$index", email = "user$index@example.com")
            )
        }
        val lecture = lectureRepository.save(
            Lecture(title = "강의1", lecturerName = "강사1", date = LocalDate.now(), registeredCount = 0)
        )
        val lectureId = lecture.id
        val userIdList = (1..40).map { it.toLong() }

        val executor: ExecutorService = Executors.newFixedThreadPool(40)
        val successCount = AtomicInteger(0)

        // when
        val tasks = userIdList.map { userId ->
            Callable {
                mockMvc.post("/api/lecture/$lectureId/register") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(LectureRegisterRequest(userId = userId))
                }.andExpect {
                    status { isCreated() }
                }
                successCount.incrementAndGet()
            }
        }

        executor.invokeAll(tasks)
        executor.shutdown()

        // then
        assertEquals(30, successCount.get())
        assertEquals(30, lectureRepository.findByIdOrNull(lectureId)!!.registeredCount)
        assertEquals(30, lectureRegistrationRepository.countByLectureId(lectureId!!))
    }

    @Test
    @Order(2)
    fun `동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공해야 한다`() {
        // given
        val user: User = userRepository.save(
            User(name = "user1", email = "user1@example.com")
        )

        val lecture: Lecture = lectureRepository.save(
            Lecture(title = "강의1", lecturerName = "강사1", date = LocalDate.now(), registeredCount = 0)
        )

        val userIdList = List(5) { user.id!! }
        val executor: ExecutorService = Executors.newFixedThreadPool(5)
        val successCount = AtomicInteger(0)

        // when
        val tasks = userIdList.map {
            Callable {
                try {
                    mockMvc.post("/api/lecture/${lecture.id!!}/register") {
                        contentType = MediaType.APPLICATION_JSON
                        content = objectMapper.writeValueAsString(LectureRegisterRequest(userId = it))
                    }.andExpect {
                        status { isCreated() }
                    }
                    successCount.incrementAndGet()
                } catch (_: Exception) {
                }
            }
        }

        executor.invokeAll(tasks)
        executor.shutdown()

        // then
        assertEquals(1, successCount.get())
        assertEquals(1, lectureRegistrationRepository.countByLectureId(lecture.id!!))
    }
}
