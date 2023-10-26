package com.kiko.yandexremoteauthapi

import com.kiko.yandexremoteauthapi.constants.YandexRemoteAuthConstants
import com.kiko.yandexremoteauthapi.data.code.module.CodeModule
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.kiko.yandexremoteauthapi.data.common.CodeYandexAuthState
import com.kiko.yandexremoteauthapi.di.RetrofitModule
import com.kiko.yandexremoteauthapi.domain.code.usecase.CodeUseCase
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import retrofit2.Retrofit

/**
 * Основной класс библиотеки, представляет клиент удаленной авторизации в Yandex
 *
 * @param baseUrl Адрес на Yandex OAuth
 */
class YandexRemoteAuth(
    val baseUrl: String = YandexRemoteAuthConstants.baseUrl
) {
    private lateinit var retrofit: Retrofit

    companion object {
        fun create(baseUrl: String = YandexRemoteAuthConstants.baseUrl): YandexRemoteAuth {
            val yandexRemoteAuth = YandexRemoteAuth(baseUrl)
            yandexRemoteAuth.retrofit =
                RetrofitModule.provideRetrofit(RetrofitModule.provideHttpClient(yandexRemoteAuth = yandexRemoteAuth))
            return yandexRemoteAuth
        }
    }

    suspend fun getCode(codeRequestEntity: CodeRequestEntity) : CodeYandexAuthState {
        val codeApi = CodeModule.provideCodeApi(retrofit)
        val codeRepository = CodeModule.provideCodeRepository(codeApi)
        return when (val codeUseCase = CodeUseCase(codeRepository).getCode(codeRequestEntity)) {
            is ApiResponse.Failure -> CodeYandexAuthState.Error(codeUseCase.message())
            is ApiResponse.Success -> CodeYandexAuthState.Success(codeUseCase.data)
        }
    }
}