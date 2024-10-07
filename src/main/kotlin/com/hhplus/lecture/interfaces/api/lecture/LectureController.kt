package com.hhplus.lecture.interfaces.api.lecture

import com.hhplus.lecture.application.lecture.LectureFacade
import com.hhplus.lecture.application.lecture.dto.LectureRegisterInfo
import com.hhplus.lecture.interfaces.api.lecture.dto.req.LectureRegisterRequest
import com.hhplus.lecture.interfaces.api.lecture.dto.res.LectureRegisterResponse
import com.hhplus.lecture.interfaces.api.lecture.dto.res.LectureResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lecture")
class LectureController(
    private val lectureFacade: LectureFacade,
) {
    @PostMapping("/{lectureId}/register")
    fun register(
        @PathVariable lectureId: Long,
        @RequestBody request: LectureRegisterRequest,
    ) : ResponseEntity<LectureRegisterResponse> {
        val result = lectureFacade.register(
            LectureRegisterInfo(
                userId = request.userId,
                lectureId = lectureId,
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(
            LectureRegisterResponse(
                userId = result.userId,
                lectureId = result.lectureId,
            )
        )
    }

    @GetMapping("/available")
    fun getAvailableLecturesByDate(
        @RequestParam date: String,
    ) : ResponseEntity<List<LectureResponse>> {
        val result = lectureFacade.getAvailableLecturesByDate(date)
        return ResponseEntity.ok().body(result.map {
            LectureResponse(
                lectureId = it.lectureId,
                title = it.title,
                lecturerName = it.lecturerName,
                date = it.date,
            )
        })
    }
}
