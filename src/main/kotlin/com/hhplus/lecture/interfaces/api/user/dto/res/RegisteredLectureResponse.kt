package com.hhplus.lecture.interfaces.api.user.dto.res

import java.time.LocalDate

data class RegisteredLectureResponse(
    val lectureId: Long,
    val title: String,
    val lecturerName: String,
    val date: LocalDate,
)
