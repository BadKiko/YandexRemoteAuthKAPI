package com.kiko.yandexremoteauthapi.data.code.repository

import com.kiko.yandexremoteauthapi.data.code.remote.api.CodeApi
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.kiko.yandexremoteauthapi.domain.code.repository.CodeRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CodeRepositoryImpl(
    private val codeApi: CodeApi
) : CodeRepository {
    override suspend fun getCode(requestEntity: CodeRequestEntity): ApiResponse<CodeResponseEntity> {
        return suspendCoroutine { suspendCoroutine ->
            codeApi.getCode(
                clientId = requestEntity.clientId,
                deviceId = requestEntity.deviceId,
                deviceName = requestEntity.deviceName
            ).request {
                suspendCoroutine.resume(
                    it
                )
            }
        }
    }
}