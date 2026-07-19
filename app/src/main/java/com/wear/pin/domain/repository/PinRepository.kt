package com.wear.pin.domain.repository

import com.wear.pin.domain.model.PinPage

interface PinRepository {
    suspend fun getBoardPins(
        boardId: String,
        bookmark: String? = null,
        pageSize: Int = 25
    ): Result<PinPage>
}
