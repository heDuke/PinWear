package com.wear.pin.data.remote.pinterest

import android.util.Base64
import com.wear.pin.core.auth.AuthConfig
import com.wear.pin.data.remote.pinterest.api.PinterestApi
import com.wear.pin.data.remote.pinterest.dto.OAuthTokenRequestDto
import com.wear.pin.data.remote.pinterest.dto.OAuthTokenResponseDto

/**
 * Remote data source specifically for OAuth operations.
 * Isolates the Basic Authentication header injection and exception handling for OAuth endpoints.
 */
class OAuthRemoteDataSource(
    private val api: PinterestApi,
    private val clientId: String = AuthConfig.CLIENT_ID,
    private val clientSecret: String = AuthConfig.CLIENT_SECRET
) {
    /**
     * Exchanges the authorization code for an access token by calling the Pinterest API.
     * Maps underlying exceptions to Result.failure.
     */
    suspend fun exchangeToken(
        code: String,
        redirectUri: String
    ): Result<OAuthTokenResponseDto> =
        runCatching {
            val credentials = "$clientId:$clientSecret"
            // Use NO_WRAP to avoid newline characters which break HTTP headers
            val authHeader =
                "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

            val requestDto = OAuthTokenRequestDto(code = code, redirectUri = redirectUri)
            api.exchangeToken(authHeader, requestDto.toFieldMap())
        }
}
