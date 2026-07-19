package com.wear.pin.domain.repository

import com.wear.pin.domain.model.BoardPage

interface BoardRepository {
    suspend fun getBoards(
        bookmark: String? = null,
        pageSize: Int = 25
    ): Result<BoardPage>
}
