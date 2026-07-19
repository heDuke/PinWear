package com.wear.pin.core.auth

import android.net.Uri

/**
 * Utility for parsing the deep link URI received from the OAuth authorization flow.
 */
object OAuthCallbackHandler {
    /**
     * Parses the intent data URI into an [OAuthCallbackResult].
     *
     * @param uri The URI received from the deep link intent.
     * @return The parsed result, or null if the URI does not match the expected scheme/host.
     */
    fun parseUri(uri: Uri?): OAuthCallbackResult? {
        if (uri == null) return null

        val expectedUri = Uri.parse(AuthConfig.REDIRECT_URI)

        // Verify scheme and host match the registered deep link
        if (uri.scheme == expectedUri.scheme && uri.host == expectedUri.host) {
            return OAuthCallbackResult(
                code = uri.getQueryParameter("code"),
                state = uri.getQueryParameter("state"),
                error = uri.getQueryParameter("error")
            )
        }

        return null
    }
}
