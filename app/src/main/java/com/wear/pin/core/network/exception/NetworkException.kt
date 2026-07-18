package com.wear.pin.core.network.exception

sealed class NetworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)

class ApiException(
    val code: Int,
    message: String,
    cause: Throwable? = null
) : NetworkException(message, cause)

class ConnectionException(
    message: String,
    cause: Throwable? = null
) : NetworkException(message, cause)

class UnknownNetworkException(
    message: String,
    cause: Throwable? = null
) : NetworkException(message, cause)
