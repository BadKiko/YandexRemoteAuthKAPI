package com.kiko.yandexremoteauthapi.data.code.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Объект запроса который необходимо отправить для получения пары кода.
 *
 * @param clientId Идентификатор приложения.
 * @param deviceId UUID для устройства с которого отправляется запрос
 * @param deviceName Имя устройства которое запрашивает авторизацю.
 */
data class CodeRequestEntity(
    val clientId: String,
    val deviceId: String,
    val deviceName: String
)
