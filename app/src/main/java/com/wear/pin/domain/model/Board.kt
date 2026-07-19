package com.wear.pin.domain.model

data class Board(
    val id: String,
    val name: String,
    val description: String?,
    val isPrivate: Boolean
)

data class BoardPage(
    val items: List<Board>,
    val bookmark: String?
)
