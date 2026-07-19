package com.wear.pin.core.auth

import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OAuthStateGeneratorTest {
    @Test
    fun `generateState returns non-empty string`() {
        val generator = OAuthStateGenerator()
        val state = generator.generateState()

        assertTrue(state.isNotEmpty())
    }

    @Test
    fun `generateState returns unique strings`() {
        val generator = OAuthStateGenerator()
        val state1 = generator.generateState()
        val state2 = generator.generateState()

        assertNotEquals(state1, state2)
    }

    @Test
    fun `generateState output is URL safe`() {
        val generator = OAuthStateGenerator()
        val state = generator.generateState()

        // Base64 URL_SAFE alphabet: A-Z, a-z, 0-9, -, _
        val isUrlSafe = state.matches(Regex("^[A-Za-z0-9\\-_]+$"))
        assertTrue("State string contains invalid characters for URL: $state", isUrlSafe)
    }
}
