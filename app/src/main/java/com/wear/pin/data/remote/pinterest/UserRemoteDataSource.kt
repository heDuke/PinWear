package com.wear.pin.data.remote.pinterest

import com.wear.pin.data.remote.pinterest.api.PinterestApi
import com.wear.pin.data.remote.pinterest.dto.UserDto

class UserRemoteDataSource(
    private val api: PinterestApi
) {
    suspend fun getUserAccount(accessToken: String): Result<UserDto> =
        runCatching {
            val authHeader = "Bearer $accessToken"
            api.getUserAccount(authHeader)
        }
}
