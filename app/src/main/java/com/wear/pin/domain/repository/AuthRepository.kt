package com.wear.pin.domain.repository

import com.wear.pin.domain.model.AuthState
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
     * Initiates the login process.
     * In this Sprint, this is a placeholder for future OAuth integration.
     */
    suspend fun login()

    /**
     * Logs out the user and clears any local authentication data.
     */
    suspend fun logout()
}
