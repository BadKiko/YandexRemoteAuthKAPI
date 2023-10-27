package com.kiko.yandexremoteauthapi.data.auth.remote.api

import com.kiko.yandexremoteauthapi.data.auth.remote.dto.AuthResponseEntity
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("token")
    @FormUrlEncoded
    fun getAuth(
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<AuthResponseEntity>
}