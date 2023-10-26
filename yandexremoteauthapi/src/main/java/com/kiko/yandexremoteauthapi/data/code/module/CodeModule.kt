package com.kiko.yandexremoteauthapi.data.code.module

import com.kiko.yandexremoteauthapi.data.code.remote.api.CodeApi
import com.kiko.yandexremoteauthapi.data.code.repository.CodeRepositoryImpl
import com.kiko.yandexremoteauthapi.domain.code.repository.CodeRepository
import retrofit2.Retrofit

object CodeModule {
    fun provideCodeApi(retrofit: Retrofit): CodeApi {
        return retrofit.create(CodeApi::class.java)
    }

    fun provideCodeRepository(
        codeApi: CodeApi
    ): CodeRepository {
        return CodeRepositoryImpl(codeApi)
    }
}