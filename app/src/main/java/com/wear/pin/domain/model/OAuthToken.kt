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
    val refreshTokenExpiresIn: Long? = null,
    val acquiredAt: Long = System.currentTimeMillis()
) {
    /**
     * Checks if the token is expired, considering a safe buffer of 60 seconds.
     */
    fun isExpired(now: Long = System.currentTimeMillis()): Boolean {
        // expiresIn is in seconds. Convert to milliseconds.
        val expirationTime = acquiredAt + (expiresIn * 1000)
        // 60 seconds safe buffer
        val safeBuffer = 60_000L
        return now > (expirationTime - safeBuffer)
    }
}
