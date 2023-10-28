package com.kiko.yandexremoteauthapi.di.auth

import com.kiko.yandexremoteauthapi.data.auth.remote.api.AuthApi
import com.kiko.yandexremoteauthapi.data.auth.repository.AuthRepositoryImpl
import com.kiko.yandexremoteauthapi.data.code.remote.api.CodeApi
import com.kiko.yandexremoteauthapi.data.code.repository.CodeRepositoryImpl
import com.kiko.yandexremoteauthapi.domain.auth.repository.AuthRepository
import com.kiko.yandexremoteauthapi.domain.code.repository.CodeRepository
import retrofit2.Retrofit

object AuthModule {
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }
}