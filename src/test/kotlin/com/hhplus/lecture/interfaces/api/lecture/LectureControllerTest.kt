package com.hhplus.lecture.interfaces.api.lecture

import com.fasterxml.jackson.databind.ObjectMapper
import com.hhplus.lecture.application.lecture.LectureFacade
import com.hhplus.lecture.application.lecture.dto.LectureInfo
import com.hhplus.lecture.application.lecture.dto.LectureRegisterInfo
import com.hhplus.lecture.interfaces.api.lecture.dto.res.LectureRegisterResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDate

@WebMvcTest(LectureController::class)
class LectureControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var lectureFacade: LectureFacade

    @Test
    fun `사용자는 특정 강의를 신청할 수 있어야 한다`() {
        // given
        val lectureId = 1L
        val userId = 1L
        val request = LectureRegisterInfo(userId = userId, lectureId = lectureId)
        val expectedResponse = LectureRegisterResponse(userId = userId, lectureId = lectureId)

        every { lectureFacade.register(request) } returns LectureRegisterInfo(userId = userId, lectureId = lectureId)

        // when, then
        mockMvc.perform(
            post("/api/lecture/$lectureId/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId", `is`(expectedResponse.userId.toInt())))
            .andExpect(jsonPath("$.lectureId", `is`(expectedResponse.lectureId.toInt())))
    }

    @Test
    fun `특정 날짜에 현재 수강 가능한 강의 목록을 조회할 수 있어야 한다`() {
        // given
        val date = "2024-10-02"
        val availableLectures = listOf(
            LectureInfo(lectureId = 1L, title = "코틀린 강의", lecturerName = "강사1", date = LocalDate.parse(date)),
            LectureInfo(lectureId = 2L, title = "자바 강의", lecturerName = "강사2", date = LocalDate.parse(date))
        )

        every { lectureFacade.getAvailableLecturesByDate(date) } returns availableLectures

        // when, then
        mockMvc.perform(
            get("/api/lecture/available")
                .param("date", date)
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
