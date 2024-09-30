package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.application.lecture.dto.LectureRegisterRequest
import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.infrastructure.repository.lecture.LectureRegistrationRepository
import com.hhplus.lecture.infrastructure.repository.lecture.LectureRepository
import com.hhplus.lecture.infrastructure.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class LectureService(
    private val userRepository: UserRepository,
    private val lectureRepository: LectureRepository,
    private val lectureRegistrationRepository: LectureRegistrationRepository,
) {
    fun register(request: LectureRegisterRequest): LectureRegistration {
        val user: User = userRepository.findById(request.userId)
            ?: throw RuntimeException("유저 정보를 찾을 수 없습니다. ID: ${request.userId}")
        val lecture: Lecture = lectureRepository.findById(request.lectureId)
            ?: throw RuntimeException("강의 정보를 찾을 수 없습니다. ID: ${request.userId}")
        return lectureRegistrationRepository.save(LectureRegistration(user = user, lecture = lecture))
    }
}
