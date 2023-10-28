package com.kiko.yandexremoteauthapi.domain.auth.repository

import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthRequestEntity
import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthResponseEntity
import com.skydoves.sandwich.ApiResponse

interface AuthRepository {
    suspend fun getAuth(requestEntity: AuthRequestEntity): ApiResponse<AuthResponseEntity>
}