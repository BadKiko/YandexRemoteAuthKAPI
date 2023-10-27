package com.kiko.yandexremoteauthapi.data.auth.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseEntity(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val  expiresIn: Int,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "token_type") val tokenType: String
)
