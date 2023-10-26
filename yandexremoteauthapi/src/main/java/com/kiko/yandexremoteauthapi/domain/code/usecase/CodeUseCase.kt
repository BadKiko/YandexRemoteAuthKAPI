package com.kiko.yandexremoteauthapi.domain.code.usecase

import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeRequestEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.kiko.yandexremoteauthapi.domain.code.repository.CodeRepository
import com.skydoves.sandwich.ApiResponse

class CodeUseCase(private val codeRepository: CodeRepository) {
    suspend fun getCode(codeRequestEntity: CodeRequestEntity): ApiResponse<CodeResponseEntity> {
        return codeRepository.getCode(codeRequestEntity)
    }
}