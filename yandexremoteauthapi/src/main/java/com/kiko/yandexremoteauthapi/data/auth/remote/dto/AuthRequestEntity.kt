package com.kiko.yandexremoteauthapi.data.auth.remote.dto

data class AuthRequestEntity(
    val grantType: String,
    val code: String,
    val clientId: String,
    val clientSecret: String,
    val interval: Int = 5,
    val expiresToken: Int = 300
)
