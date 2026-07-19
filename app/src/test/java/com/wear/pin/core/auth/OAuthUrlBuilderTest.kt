package com.wear.pin.core.auth

import org.junit.Assert.assertTrue
import org.junit.Test

class OAuthUrlBuilderTest {
    @Test
    fun `buildUrl constructs correct url with default scopes`() {
        val builder = OAuthUrlBuilder()
        val url =
            builder.buildUrl(
                clientId = "12345",
                redirectUri = "pinwear://oauth",
                state = "random-state"
            )

        assertTrue(url.startsWith("https://www.pinterest.com/oauth/"))
        assertTrue(url.contains("client_id=12345"))
        assertTrue(url.contains("redirect_uri=pinwear%3A%2F%2Foauth"))
        assertTrue(url.contains("response_type=code"))
        assertTrue(url.contains("scope=user_accounts%3Aread%2Cboards%3Aread%2Cpins%3Aread"))
        assertTrue(url.contains("state=random-state"))
    }

    @Test
    fun `buildUrl constructs correct url with custom scopes`() {
        val builder = OAuthUrlBuilder()
        val url =
            builder.buildUrl(
                clientId = "12345",
                redirectUri = "pinwear://oauth",
                state = "random-state",
                scopes = "user_accounts:read"
            )

        assertTrue(url.contains("scope=user_accounts%3Aread"))
    }
}
