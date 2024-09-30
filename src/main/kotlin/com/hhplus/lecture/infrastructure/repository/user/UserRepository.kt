package com.hhplus.lecture.infrastructure.repository.user

import com.hhplus.lecture.domain.user.User

interface UserRepository {
    fun findById(userId: Long): User?
}
