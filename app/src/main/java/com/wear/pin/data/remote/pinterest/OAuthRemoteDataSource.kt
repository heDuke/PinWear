package com.wear.pin.data.remote.pinterest

import android.util.Base64
import com.wear.pin.core.auth.AuthConfig
import com.wear.pin.data.remote.pinterest.api.PinterestApi
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
     * Exchanges an authorization code for an OAuth token.
     */
    suspend fun exchangeToken(
        code: String,
        redirectUri: String
    ): Result<OAuthTokenResponseDto> =
        runCatching {
            val authHeader = getBasicAuthHeader()
            val fields =
                mapOf(
                    "grant_type" to "authorization_code",
                    "code" to code,
                    "redirect_uri" to redirectUri
                )
            api.exchangeToken(authHeader, fields)
        }

    /**
     * Refreshes an OAuth token using the refresh token.
     */
    suspend fun refreshToken(refreshToken: String): Result<OAuthTokenResponseDto> =
        runCatching {
            val authHeader = getBasicAuthHeader()
            val fields =
                mapOf(
                    "grant_type" to "refresh_token",
                    "refresh_token" to refreshToken
                )
            api.refreshToken(authHeader, fields)
        }

    private fun getBasicAuthHeader(): String {
        val credentials = "$clientId:$clientSecret"
        return "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
    }
}
