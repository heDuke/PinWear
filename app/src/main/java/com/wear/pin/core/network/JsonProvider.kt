package com.wear.pin.core.network

import kotlinx.serialization.json.Json

object JsonProvider {
    val networkJson =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            isLenient = true
        }
}
