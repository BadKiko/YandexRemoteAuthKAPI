package com.kiko.yandexremoteauthapi

import android.util.Log
import com.kiko.yandexremoteauthapi.constants.YandexRemoteAuthConstants
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthRequestEntity
import com.kiko.yandexremoteauthapi.di.code.CodeModule
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.kiko.yandexremoteauthapi.data.common.AuthYandexAuthState
import com.kiko.yandexremoteauthapi.data.common.CodeYandexAuthState
import com.kiko.yandexremoteauthapi.di.auth.AuthModule
import com.kiko.yandexremoteauthapi.di.networking.RetrofitModule
import com.kiko.yandexremoteauthapi.domain.auth.usecase.AuthUseCase
import com.kiko.yandexremoteauthapi.domain.code.usecase.CodeUseCase
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapFailure
import com.skydoves.sandwich.suspendMapSuccess
import kotlinx.coroutines.delay
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

    suspend fun getCode(codeRequestEntity: CodeRequestEntity): CodeYandexAuthState {
        val codeApi = CodeModule.provideCodeApi(retrofit)
        val codeRepository = CodeModule.provideCodeRepository(codeApi)
        return when (val codeUseCase = CodeUseCase(codeRepository).getCode(codeRequestEntity)) {
            is ApiResponse.Failure -> CodeYandexAuthState.Error(codeUseCase.message())
            is ApiResponse.Success -> CodeYandexAuthState.Success(codeUseCase.data)
        }
    }

    suspend fun getAuth(authRequestEntity: AuthRequestEntity) : AuthYandexAuthState {
        val authApi = AuthModule.provideAuthApi(retrofit)
        val authRepository = AuthModule.provideAuthRepository(authApi)

        // Количество попыток
        val repeats = 0
        val maxRepeats: Int = authRequestEntity.expiresToken / authRequestEntity.interval

        for (repeat in repeats..maxRepeats) {
            delay(authRequestEntity.interval.toLong() * 1000)

            Log.d(YandexRemoteAuthConstants.TAG,"Try to auth repeat: $repeat")

            val authResponse = AuthUseCase(authRepository).getAuth(
                authRequestEntity
            )
            if (
                authResponse is ApiResponse.Success
            ) {
                Log.d(YandexRemoteAuthConstants.TAG,"Success auth, data: ${authResponse.data}, repeat $repeat")
                return AuthYandexAuthState.Success(authResponse.data)
            }
        }
        Log.d(YandexRemoteAuthConstants.TAG,"Error auth")
        return AuthYandexAuthState.Error("Timeout of auth")
    }
}