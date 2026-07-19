package com.wear.pin.data.repository

import com.wear.pin.data.remote.pinterest.BoardRemoteDataSource
import com.wear.pin.data.remote.pinterest.mapper.BoardMapper.toDomain
import com.wear.pin.domain.model.BoardPage
import com.wear.pin.domain.repository.AuthRepository
import com.wear.pin.domain.repository.BoardRepository

class BoardRepositoryImpl(
    private val remoteDataSource: BoardRemoteDataSource,
    private val authRepository: AuthRepository
) : BoardRepository {
    override suspend fun getBoards(
        bookmark: String?,
        pageSize: Int
    ): Result<BoardPage> {
        val token =
            authRepository.getValidToken()
                ?: return Result.failure(IllegalStateException("No valid token available"))

        return remoteDataSource
            .getBoards(token.accessToken, bookmark, pageSize)
            .map { it.toDomain() }
    }
}
