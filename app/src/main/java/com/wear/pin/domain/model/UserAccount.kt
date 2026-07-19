package com.wear.pin.domain.model

/**
 * Represents a Pinterest user account.
 */
data class UserAccount(
    val username: String,
    val profileImageUrl: String?,
    val accountType: String,
    val about: String?
)
