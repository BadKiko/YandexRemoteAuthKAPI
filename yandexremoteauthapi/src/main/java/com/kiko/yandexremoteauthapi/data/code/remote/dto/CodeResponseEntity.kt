package com.kiko.yandexremoteauthapi.data.code.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Ответ от OAuth который выдает код
 *
 * @param deviceCode Код, с которым следует запрашивать OAuth-токен.
 * @param expiresIn Срок действия пары кодов в секундах.
 * @param interval Минимальный интервал в секундах, с которым приложение должно запрашивать OAuth-токен.
 * @param userCode Код, который должен ввести пользователь, чтобы разрешить доступ к своим данным.
 * @param verificationUrl Адрес страницы, на которой пользователь должен ввести код.
 */
@JsonClass(generateAdapter = true)
data class CodeResponseEntity(
    @Json(name = "device_code") val deviceCode: String,
    @Json(name = "expires_in") val expiresIn: Int,
    val interval: Int,
    @Json(name = "user_code") val userCode: String,
    @Json(name = "verification_url") val verificationUrl: String
)
