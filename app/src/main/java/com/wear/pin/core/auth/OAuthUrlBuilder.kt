package com.wear.pin.core.auth

import java.net.URLEncoder

/**
 * Utility for building the Pinterest OAuth authorization URL.
 */
class OAuthUrlBuilder {
    companion object {
        private const val PINTEREST_AUTH_URL = "https://www.pinterest.com/oauth/"
        private const val DEFAULT_SCOPES = "user_accounts:read,boards:read,pins:read"
    }

    /**
     * Builds the authorization URL for Pinterest OAuth.
     *
     * @param clientId The application's Client ID.
     * @param redirectUri The registered redirect URI for the application.
     * @param state A random string to prevent CSRF attacks.
     * @param scopes A comma-separated list of required scopes. Defaults to MVP scopes.
     * @return The complete authorization URL as a String.
     */
    fun buildUrl(
        clientId: String,
        redirectUri: String,
        state: String,
        scopes: String = DEFAULT_SCOPES
    ): String {
        val encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8")
        val encodedScopes = URLEncoder.encode(scopes, "UTF-8")
        val encodedState = URLEncoder.encode(state, "UTF-8")

        return "$PINTEREST_AUTH_URL?" +
            "client_id=$clientId&" +
            "redirect_uri=$encodedRedirectUri&" +
            "response_type=code&" +
            "scope=$encodedScopes&" +
            "state=$encodedState"
    }
}
