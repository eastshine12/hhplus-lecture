package com.hhplus.lecture.interfaces.api.lecture.dto.res

import java.time.LocalDate

data class LectureResponse(
    val lectureId: Long,
    val title: String,
    val lecturerName: String,
    val date: LocalDate,
)
