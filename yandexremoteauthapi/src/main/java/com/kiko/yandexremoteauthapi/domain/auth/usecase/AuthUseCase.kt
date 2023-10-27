package com.kiko.yandexremoteauthapi.domain.auth.usecase

import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthRequestEntity
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthResponseEntity
import com.kiko.yandexremoteauthapi.domain.auth.repository.AuthRepository
import com.skydoves.sandwich.ApiResponse

class AuthUseCase(private val authRepository: AuthRepository) {
    suspend fun getAuth(authRequestEntity: AuthRequestEntity): ApiResponse<AuthResponseEntity> {
        return authRepository.getCode(authRequestEntity)
    }
}