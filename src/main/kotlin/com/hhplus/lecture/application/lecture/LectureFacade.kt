package com.hhplus.lecture.application.lecture

import com.hhplus.lecture.application.lecture.dto.LectureInfo
import com.hhplus.lecture.application.lecture.dto.LectureRegisterInfo
import com.hhplus.lecture.application.lecture.mapper.LectureMapper
import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRegistration
import com.hhplus.lecture.domain.lecture.LectureService
import com.hhplus.lecture.domain.user.User
import com.hhplus.lecture.domain.user.UserService
import com.hhplus.lecture.util.DateValidator
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LectureFacade(
    private val userService: UserService,
    private val lectureService: LectureService,
    private val lectureMapper: LectureMapper,
) {
    fun register(
        info: LectureRegisterInfo,
    ) : LectureRegisterInfo {
        val user: User = userService.findById(info.userId)
        val lectureRegistration: LectureRegistration = lectureService.register(user, info.lectureId)
        return lectureMapper.toRegisterInfo(lectureRegistration)
    }

    fun getAvailableLecturesByDate(
        dateString: String,
    ) : List<LectureInfo> {
        val date: LocalDate = DateValidator.parseDate(dateString)
        val availableLectures: List<Lecture> = lectureService.getAvailableLecturesByDate(date)
        return lectureMapper.toLectureInfoList(availableLectures)
    }

    fun getRegisteredLecturesByUserId(
        userId: Long,
    ) : List<LectureInfo> {
        val user: User = userService.findById(userId)
        val registeredLectures: List<Lecture> = lectureService.getLecturesByUserId(user)
        return lectureMapper.toLectureInfoList(registeredLectures)
    }
}
