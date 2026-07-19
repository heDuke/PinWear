package com.wear.pin.domain.repository

import com.wear.pin.domain.model.AuthState
import com.wear.pin.domain.model.OAuthToken
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining authentication operations.
 */
interface AuthRepository {
    /**
     * Observes the current authentication state.
     */
    fun getAuthState(): Flow<AuthState>

    /**
     * Builds the OAuth authorization URL.
     *
     * @return The complete authorization URL as a String.
     */
    fun buildAuthorizationUrl(): String

    /**
     * Handles the OAuth callback response.
     *
     * @param code The authorization code, if successful.
     * @param state The state string.
     * @param error The error message, if failed.
     */
    suspend fun handleAuthorizationResponse(
        code: String?,
        state: String?,
        error: String?
    )

    /**
     * Exchanges the authorization code for an access token.
     *
     * @param code The authorization code received from the callback.
     * @return The OAuthToken if successful.
     */
    suspend fun exchangeCodeForToken(code: String): Result<com.wear.pin.domain.model.OAuthToken>

    /**
     * Checks the local token to restore the session on app startup.
     * Note (Sprint 4E/4F): Restores and verifies expiration.
     */
    suspend fun restoreSession()

    /**
     * Returns a valid OAuth token if available.
     * If the current token is expired, this will attempt to refresh it.
     * @return A valid OAuthToken, or null if unauthenticated or refresh fails.
     */
    suspend fun getValidToken(): OAuthToken?

    /**
     * Explicitly refreshes the current OAuth token.
     */
    suspend fun refreshToken(): Result<OAuthToken>

    /**
     * Initiates the login process.
     * In this Sprint, this is a placeholder for future OAuth integration.
     */
    suspend fun login()

    /**
     * Logs out the user and clears any local authentication data.
     */
    suspend fun logout()
}
