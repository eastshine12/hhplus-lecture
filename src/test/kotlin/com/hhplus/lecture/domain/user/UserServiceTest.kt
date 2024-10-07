package com.hhplus.lecture.domain.user

import com.hhplus.lecture.domain.common.exception.UserNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        userService = UserService(userRepository)
    }

    @Test
    fun `존재하지 않는 유저ID를 조회하면 예외가 발생해야 한다`() {
        // given
        val userId = 1L
        every { userRepository.findByIdOrNull(userId) } returns null

        // when, then
        val exception = assertThrows<UserNotFoundException> {
            userService.findById(userId)
        }
        assertEquals("유저 정보를 찾을 수 없습니다. ID: $userId", exception.message)
    }
}
