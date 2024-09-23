package com.example.morningroutine.di

import androidx.compose.ui.util.trace
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {

    private const val OKHTTP_TIMEOUT = 10000L

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = trace("MrOkHttp") {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

}