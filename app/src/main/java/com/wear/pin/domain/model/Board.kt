package com.wear.pin.domain.model

/**
 * Represents a Pinterest board.
 */
data class Board(
    val id: String,
    val name: String,
    val description: String?,
    val pinCount: Int
)
