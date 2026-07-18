package com.wear.pin.domain.model

/**
 * Represents a Pinterest pin.
 */
data class Pin(
    val id: String,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val link: String?
)
