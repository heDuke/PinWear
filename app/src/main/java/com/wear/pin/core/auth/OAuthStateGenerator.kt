package com.wear.pin.core.auth

import java.security.SecureRandom
import java.util.Base64

/**
 * Utility for generating secure, random state strings for OAuth CSRF protection.
 */
class OAuthStateGenerator {
    private val secureRandom = SecureRandom()

    /**
     * Generates a random, URL-safe Base64 encoded string.
     *
     * @param byteLength The number of random bytes to generate before encoding. Defaults to 32.
     * @return A URL-safe string.
     */
    fun generateState(byteLength: Int = 32): String {
        val bytes = ByteArray(byteLength)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}
