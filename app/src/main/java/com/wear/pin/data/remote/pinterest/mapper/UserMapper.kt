package com.wear.pin.data.remote.pinterest.mapper

import com.wear.pin.data.remote.pinterest.dto.UserDto
import com.wear.pin.domain.model.UserAccount

object UserMapper {
    fun UserDto.toDomain(): UserAccount =
        UserAccount(
            username = this.username.orEmpty(),
            profileImageUrl = this.profileImage,
            accountType = this.accountType ?: "UNKNOWN",
            about = this.about
        )
}
