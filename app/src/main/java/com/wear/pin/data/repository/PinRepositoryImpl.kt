package com.wear.pin.data.repository

import com.wear.pin.data.remote.pinterest.PinRemoteDataSource
import com.wear.pin.data.remote.pinterest.mapper.PinMapper.toDomain
import com.wear.pin.domain.model.PinPage
import com.wear.pin.domain.repository.AuthRepository
import com.wear.pin.domain.repository.PinRepository

class PinRepositoryImpl(
    private val remoteDataSource: PinRemoteDataSource,
    private val authRepository: AuthRepository
) : PinRepository {
    override suspend fun getBoardPins(
        boardId: String,
        bookmark: String?,
        pageSize: Int
    ): Result<PinPage> {
        val token =
            authRepository.getValidToken()
                ?: return Result.failure(IllegalStateException("No valid token available"))

        return remoteDataSource
            .getBoardPins(token.accessToken, boardId, bookmark, pageSize)
            .map { it.toDomain() }
    }
}
