package com.wear.pin.domain.model

/**
 * Represents a Pinterest user account.
 */
data class UserAccount(
    val id: String,
    val username: String,
    val profileImageUrl: String?
)
