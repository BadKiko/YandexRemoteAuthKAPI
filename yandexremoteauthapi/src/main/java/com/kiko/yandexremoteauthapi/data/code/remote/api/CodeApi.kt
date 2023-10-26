package com.kiko.yandexremoteauthapi.data.code.remote.api

import com.kiko.yandexremoteauthapi.data.code.remote.dto.CodeResponseEntity
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CodeApi {
    @POST("device/code")
    @FormUrlEncoded
    fun getCode(
        @Field("client_id") clientId: String,
        @Field("device_id") deviceId: String,
        @Field("device_name") deviceName: String
    ): Call<CodeResponseEntity>
}