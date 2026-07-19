package com.wear.pin.data.remote.pinterest

import com.wear.pin.data.remote.pinterest.api.PinterestApi
import com.wear.pin.data.remote.pinterest.dto.PageDto
import com.wear.pin.data.remote.pinterest.dto.PinDto

class PinRemoteDataSource(
    private val api: PinterestApi
) {
    suspend fun getBoardPins(
        accessToken: String,
        boardId: String,
        bookmark: String? = null,
        pageSize: Int = 25
    ): Result<PageDto<PinDto>> =
        runCatching {
            val authHeader = "Bearer $accessToken"
            api.getBoardPins(authHeader, boardId, bookmark, pageSize)
        }
}
