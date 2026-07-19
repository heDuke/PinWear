package com.wear.pin.domain.repository

import com.wear.pin.domain.model.OAuthToken

/**
 * Interface defining the contract for securely storing and retrieving the OAuth Token.
 */
interface TokenStorage {
    /**
     * Secures and persists the provided token.
     */
    suspend fun saveToken(token: OAuthToken)

    /**
     * Loads and decrypts the persisted token.
     * @return The OAuthToken if it exists and can be decrypted, null otherwise.
     */
    suspend fun loadToken(): OAuthToken?

    /**
     * Clears the persisted token.
     */
    suspend fun clearToken()
}
