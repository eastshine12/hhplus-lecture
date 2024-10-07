package com.hhplus.lecture.domain.user

import com.hhplus.lecture.domain.common.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id)
            ?: throw UserNotFoundException("유저 정보를 찾을 수 없습니다. ID: $id")
    }
}
