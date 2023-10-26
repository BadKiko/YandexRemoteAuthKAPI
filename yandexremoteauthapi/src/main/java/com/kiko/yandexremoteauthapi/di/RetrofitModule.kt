package com.kiko.yandexremoteauthapi.di

import com.kiko.yandexremoteauthapi.YandexRemoteAuth
import com.kiko.yandexremoteauthapi.constants.YandexRemoteAuthConstants
import com.kiko.yandexremoteauthapi.data.remote.interceptor.YandexUrlInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitModule {

    fun provideHttpClient(
        yandexRemoteAuth: YandexRemoteAuth
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(YandexUrlInterceptor(yandexRemoteAuth))
            .build()
    }

    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(YandexRemoteAuthConstants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}