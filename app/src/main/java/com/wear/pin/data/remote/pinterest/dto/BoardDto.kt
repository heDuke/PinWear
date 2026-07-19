package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("privacy") val privacy: String? = null
)
