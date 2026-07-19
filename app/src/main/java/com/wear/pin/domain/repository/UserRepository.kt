package com.wear.pin.domain.repository

import com.wear.pin.domain.model.UserAccount

interface UserRepository {
    suspend fun getCurrentUser(): Result<UserAccount>
}
