package com.hhplus.lecture.util

import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateValidator {
    companion object {
        fun parseDate(dateString: String): LocalDate {
            return try {
                LocalDate.parse(dateString)
            } catch (e: DateTimeParseException) {
                throw IllegalArgumentException("유효하지 않은 날짜 형식입니다: $dateString")
            }
        }
    }
}