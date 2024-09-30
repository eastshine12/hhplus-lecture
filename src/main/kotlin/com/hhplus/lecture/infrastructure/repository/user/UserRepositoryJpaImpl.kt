package com.hhplus.lecture.infrastructure.repository.user

import com.hhplus.lecture.domain.user.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryJpaImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
    }
}
