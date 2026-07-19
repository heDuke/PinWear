package com.wear.pin.domain.model

data class Pin(
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val link: String?
)

data class PinPage(
    val items: List<Pin>,
    val bookmark: String?
)
