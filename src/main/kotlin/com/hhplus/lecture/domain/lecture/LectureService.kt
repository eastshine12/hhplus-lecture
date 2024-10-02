package com.hhplus.lecture.domain.lecture

import com.hhplus.lecture.domain.common.exception.LectureNotFoundException
import com.hhplus.lecture.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional(readOnly = true)
@Service
class LectureService(
    private val lectureRepository: LectureRepository,
    private val lectureRegistrationRepository: LectureRegistrationRepository,
) {

    fun findById(id: Long): Lecture {
        return lectureRepository.findByIdOrNull(id)
            ?: throw LectureNotFoundException("강의 정보를 찾을 수 없습니다. ID: $id")
    }

    @Transactional
    fun register(user: User, lectureId: Long): LectureRegistration {
        val lecture = findById(lectureId).incrementRegisteredCount()
        return lectureRegistrationRepository.save(LectureRegistration(user = user, lecture = lecture))
    }

    fun getAvailableLecturesByDate(date: LocalDate): List<Lecture> {
        return lectureRepository.findAvailableLecturesByDate(date, Lecture.MAX_SEATS)
    }

    fun getLecturesByUserId(user: User): List<Lecture> {
        return lectureRegistrationRepository.findLecturesByUser(user)
    }
}
