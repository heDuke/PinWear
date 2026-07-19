package com.wear.pin.data.repository

import com.wear.pin.data.remote.pinterest.UserRemoteDataSource
import com.wear.pin.data.remote.pinterest.mapper.UserMapper.toDomain
import com.wear.pin.domain.model.UserAccount
import com.wear.pin.domain.repository.AuthRepository
import com.wear.pin.domain.repository.UserRepository

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val authRepository: AuthRepository
) : UserRepository {
    override suspend fun getCurrentUser(): Result<UserAccount> {
        val token =
            authRepository.getValidToken()
                ?: return Result.failure(IllegalStateException("No valid token available"))

        return remoteDataSource.getUserAccount(token.accessToken).map { it.toDomain() }
    }
}
