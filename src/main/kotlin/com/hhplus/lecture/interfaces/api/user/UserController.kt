package com.hhplus.lecture.interfaces.api.user

import com.hhplus.lecture.application.lecture.LectureFacade
import com.hhplus.lecture.interfaces.api.user.dto.res.RegisteredLectureResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val lectureFacade: LectureFacade,
) {

    @GetMapping("/{userId}/lecture/registered")
    fun getRegisteredLecturesById(
        @PathVariable("userId") userId: Long
    ) : ResponseEntity<List<RegisteredLectureResponse>> {
        val result = lectureFacade.getRegisteredLecturesByUserId(userId)
        return ResponseEntity.ok().body(result.map {
            RegisteredLectureResponse(
                lectureId = it.lectureId,
                title = it.title,
                lecturerName = it.lecturerName,
                date = it.date,
            )
        })
    }
}
