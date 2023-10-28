package com.kiko.yandexremoteauthapi.data.auth.repository

import com.kiko.yandexremoteauthapi.data.auth.remote.api.AuthApi
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthRequestEntity
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthResponseEntity
import com.kiko.yandexremoteauthapi.domain.auth.repository.AuthRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl(private val authApi: AuthApi) : AuthRepository {

    override suspend fun getAuth(requestEntity: AuthRequestEntity): ApiResponse<AuthResponseEntity> {
        while (true) {
            return suspendCoroutine { suspendCoroutine ->
                authApi.getAuth(
                    grantType = requestEntity.grantType,
                    code = requestEntity.code,
                    clientId = requestEntity.clientId,
                    clientSecret = requestEntity.clientSecret
                ).request {
                    suspendCoroutine.resume(it)
                }
            }
        }
    }
}