package com.hhplus.lecture.application.lecture.mapper

import com.hhplus.lecture.application.lecture.dto.LectureInfo
import com.hhplus.lecture.application.lecture.dto.LectureRegisterInfo
import com.hhplus.lecture.domain.lecture.Lecture
import com.hhplus.lecture.domain.lecture.LectureRegistration
import org.springframework.stereotype.Component

@Component
class LectureMapper {
    fun toRegisterInfo(lectureRegistration: LectureRegistration): LectureRegisterInfo {
        return LectureRegisterInfo(
            userId = lectureRegistration.user.id ?: throw IllegalArgumentException("사용자 ID는 null일 수 없습니다."),
            lectureId = lectureRegistration.lecture.id ?: throw IllegalArgumentException("강의 ID는 null일 수 없습니다.")
        )
    }

    fun toLectureInfoList(lectures: List<Lecture>): List<LectureInfo> {
        return lectures.map { lecture ->
            LectureInfo(
                lectureId = lecture.id ?: throw IllegalArgumentException("강의 ID는 null일 수 없습니다."),
                title = lecture.title,
                lecturerName = lecture.lecturerName,
                date = lecture.date,
            )
        }
    }
}
