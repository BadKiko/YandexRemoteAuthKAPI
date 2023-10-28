package com.kiko.yandexremoteauthapi.data.common

import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthResponseEntity
import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import com.skydoves.sealedx.core.Extensive
import com.skydoves.sealedx.core.annotations.ExtensiveModel
import com.skydoves.sealedx.core.annotations.ExtensiveSealed

@ExtensiveSealed(
    models = [
        ExtensiveModel(type = CodeResponseEntity::class, name = "Code"),
        ExtensiveModel(type = AuthResponseEntity::class, name = "Auth")
    ]
)
/**
 * Состояния возвращения результата
 */
sealed interface YandexAuthState {
    data class Success(val data: Extensive) : YandexAuthState
    data class Error(val message: String) : YandexAuthState
}