package com.wear.pin.data.mapper

import com.wear.pin.data.remote.pinterest.dto.OAuthTokenResponseDto
import com.wear.pin.domain.model.OAuthToken

fun OAuthTokenResponseDto.toDomain(): OAuthToken =
    OAuthToken(
        accessToken = this.accessToken,
        tokenType = this.tokenType,
        expiresIn = this.expiresIn,
        scope = this.scope,
        refreshToken = this.refreshToken,
        refreshTokenExpiresIn = this.refreshTokenExpiresIn
    )
