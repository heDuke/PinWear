package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageDto<T>(
    val items: List<T>,
    val bookmark: String? = null
    // TODO: 如果官方 API Reference 在具体 endpoint 包含其他分页元数据字段，将在此处补充
)
