package com.wear.pin.di

import android.content.Context
import com.wear.pin.core.network.OkHttpProvider
import com.wear.pin.core.network.RetrofitProvider
import com.wear.pin.data.local.DataStoreTokenStorage
import com.wear.pin.data.remote.pinterest.BoardRemoteDataSource
import com.wear.pin.data.remote.pinterest.OAuthRemoteDataSource
import com.wear.pin.data.remote.pinterest.PinRemoteDataSource
import com.wear.pin.data.remote.pinterest.UserRemoteDataSource
import com.wear.pin.data.remote.pinterest.api.PinterestApi
import com.wear.pin.data.repository.AuthRepositoryImpl
import com.wear.pin.data.repository.BoardRepositoryImpl
import com.wear.pin.data.repository.PinRepositoryImpl
import com.wear.pin.data.repository.UserRepositoryImpl
import com.wear.pin.domain.repository.AuthRepository
import com.wear.pin.domain.repository.BoardRepository
import com.wear.pin.domain.repository.PinRepository
import com.wear.pin.domain.repository.UserRepository

/**
 * Manual Dependency Injection container.
 * Lives as long as the Application.
 */
class AppContainer(
    private val applicationContext: Context
) {
    // Core Network
    private val okHttpClient by lazy {
        OkHttpProvider.provideOkHttpClient()
    }

    private val retrofit by lazy {
        RetrofitProvider.provideRetrofit(okHttpClient)
    }

    private val pinterestApi by lazy {
        retrofit.create(PinterestApi::class.java)
    }

    // Local Storage
    private val tokenStorage by lazy {
        DataStoreTokenStorage(applicationContext)
    }

    // Auth
    private val oauthRemoteDataSource by lazy {
        OAuthRemoteDataSource(pinterestApi)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            remoteDataSource = oauthRemoteDataSource,
            tokenStorage = tokenStorage
        )
    }

    // User Profile
    private val userRemoteDataSource by lazy {
        UserRemoteDataSource(pinterestApi)
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            remoteDataSource = userRemoteDataSource,
            authRepository = authRepository
        )
    }

    // Boards
    private val boardRemoteDataSource by lazy {
        BoardRemoteDataSource(pinterestApi)
    }

    val boardRepository: BoardRepository by lazy {
        BoardRepositoryImpl(
            remoteDataSource = boardRemoteDataSource,
            authRepository = authRepository
        )
    }

    // Pins
    private val pinRemoteDataSource by lazy {
        PinRemoteDataSource(pinterestApi)
    }

    val pinRepository: PinRepository by lazy {
        PinRepositoryImpl(
            remoteDataSource = pinRemoteDataSource,
            authRepository = authRepository
        )
    }
}
