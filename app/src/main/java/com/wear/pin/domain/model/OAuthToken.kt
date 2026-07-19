package com.wear.pin.domain.model

/**
 * Represents an OAuth token pair for Pinterest API authentication.
 */
data class OAuthToken(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
    val refreshToken: String?,
    val refreshTokenExpiresIn: Long? = null
)
