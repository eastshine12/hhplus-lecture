package com.hhplus.lecture.interfaces.api.user

import com.hhplus.lecture.application.lecture.LectureFacade
import com.hhplus.lecture.application.lecture.dto.LectureInfo
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(UserController::class)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var lectureFacade: LectureFacade

    @Test
    fun `특정 유저의 강의 신청 완료 목록을 조회할 수 있어야 한다`() {
        // given
        val userId = 1L
        val registeredLectures = listOf(
            LectureInfo(lectureId = 1L, title = "코틀린 강의", lecturerName = "강사1", date = LocalDate.now()),
            LectureInfo(lectureId = 2L, title = "자바 강의", lecturerName = "강사2", date = LocalDate.now())
        )

        every { lectureFacade.getRegisteredLecturesByUserId(userId) } returns registeredLectures

        // when, then
        mockMvc.perform(
            get("/api/user/$userId/lecture/registered")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].lectureId", `is`(1)))
            .andExpect(jsonPath("$[0].title", `is`("코틀린 강의")))
            .andExpect(jsonPath("$[0].lecturerName", `is`("강사1")))
            .andExpect(jsonPath("$[1].lectureId", `is`(2)))
            .andExpect(jsonPath("$[1].title", `is`("자바 강의")))
            .andExpect(jsonPath("$[1].lecturerName", `is`("강사2")))

    }
}