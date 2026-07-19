package com.wear.pin.data.remote.pinterest.dto

/**
 * Data Transfer Object for OAuth Token Exchange Request.
 * Note: Pinterest requires this to be form URL encoded, so this object is converted to a FieldMap.
 */
data class OAuthTokenRequestDto(
    val code: String,
    val redirectUri: String,
    val grantType: String = "authorization_code"
) {
    fun toFieldMap(): Map<String, String> =
        mapOf(
            "grant_type" to grantType,
            "code" to code,
            "redirect_uri" to redirectUri
        )
}
