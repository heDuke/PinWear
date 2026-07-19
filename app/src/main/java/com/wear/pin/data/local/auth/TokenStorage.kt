package com.wear.pin.data.local.auth

/**
 * Interface defining secure token storage operations.
 */
interface TokenStorage {
    /**
     * Saves the access token and an optional refresh token securely.
     */
    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String?
    )

    /**
     * Retrieves the access token, if any.
     */
    suspend fun getAccessToken(): String?

    /**
     * Retrieves the refresh token, if any.
     */
    suspend fun getRefreshToken(): String?

    /**
     * Clears all stored tokens.
     */
    suspend fun clear()
}
