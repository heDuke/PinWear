package com.wear.pin.data.remote.pinterest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("account_type") val accountType: String? = null,
    @SerialName("profile_image") val profileImage: String? = null,
    @SerialName("website_url") val websiteUrl: String? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("business_name") val businessName: String? = null,
    @SerialName("pin_count") val pinCount: Int? = null,
    @SerialName("follower_count") val followerCount: Int? = null,
    @SerialName("following_count") val followingCount: Int? = null,
    @SerialName("board_count") val boardCount: Int? = null
)
