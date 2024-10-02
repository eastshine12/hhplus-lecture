package com.hhplus.lecture.application.lecture.dto

import java.time.LocalDate

data class LectureInfo(
    val lectureId: Long,
    val title: String,
    val lecturerName: String,
    val date: LocalDate,
)
