package com.hhplus.lecture.interfaces.api.lecture.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class LectureRegisterRequest(
    @JsonProperty("userId")
    val userId: Long,
)
