package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    // TODO: 待官方 API Reference 核验具体字段，不得根据 Domain Model 推测
    val _placeholder: String? = null
)
