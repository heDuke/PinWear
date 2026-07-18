package com.wear.pin.domain.model

/**
 * Represents an OAuth token pair for Pinterest API authentication.
 */
data class OAuthToken(
    val accessToken: String,
    val refreshToken: String?,
    val scope: String?,
    val tokenType: String
)
