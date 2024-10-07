package com.hhplus.lecture.domain.user

interface UserRepository {
    fun findByIdOrNull(userId: Long): User?
}
