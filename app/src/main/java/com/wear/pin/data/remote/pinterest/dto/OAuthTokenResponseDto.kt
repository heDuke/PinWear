package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for OAuth Token Exchange Response.
 */
@Serializable
data class OAuthTokenResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("response_type")
    val responseType: String? = null,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("refresh_token_expires_in")
    val refreshTokenExpiresIn: Long? = null
)
