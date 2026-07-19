package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PinDto(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("link") val link: String? = null,
    @SerialName("media") val media: PinMediaDto? = null
)

@Serializable
data class PinMediaDto(
    @SerialName("images") val images: Map<String, PinImageDto>? = null
)

@Serializable
data class PinImageDto(
    @SerialName("url") val url: String? = null
)
