package com.kiko.yandexremoteauthapi.domain.code.repository

import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.skydoves.sandwich.ApiResponse

interface CodeRepository {
    suspend fun getCode(requestEntity: CodeRequestEntity) : ApiResponse<CodeResponseEntity>
}