package com.wear.pin.data.remote.pinterest

import com.wear.pin.data.remote.pinterest.api.PinterestApi
import com.wear.pin.data.remote.pinterest.dto.BoardDto
import com.wear.pin.data.remote.pinterest.dto.PageDto

class BoardRemoteDataSource(
    private val api: PinterestApi
) {
    suspend fun getBoards(
        accessToken: String,
        bookmark: String? = null,
        pageSize: Int = 25
    ): Result<PageDto<BoardDto>> =
        runCatching {
            val authHeader = "Bearer $accessToken"
            api.getBoards(authHeader, bookmark, pageSize)
        }
}
