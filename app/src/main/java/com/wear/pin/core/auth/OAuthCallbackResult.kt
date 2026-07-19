package com.wear.pin.core.auth

/**
 * Represents the parsed result of an OAuth callback URI.
 */
data class OAuthCallbackResult(
    val code: String?,
    val state: String?,
    val error: String?
)
