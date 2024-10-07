package com.hhplus.lecture.interfaces.advice

import com.hhplus.lecture.domain.common.exception.LectureNotFoundException
import com.hhplus.lecture.domain.common.exception.MaxSeatsReachedException
import com.hhplus.lecture.domain.common.exception.UserNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(LectureNotFoundException::class)
    fun handleLectureNotFoundException(ex: LectureNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler(MaxSeatsReachedException::class)
    fun handleMaxSeatsReachedException(ex: MaxSeatsReachedException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message ?: "잘못된 요청입니다.", HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleUniqueConstraintViolation(ex: DataIntegrityViolationException): ResponseEntity<String> {
        return ResponseEntity("이미 신청한 강의입니다.", HttpStatus.BAD_REQUEST)
    }
}
