package com.example.morningroutine.core.network

import androidx.compose.ui.util.trace
import com.example.morningroutine.ui.routine.StockInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface MrNetworkDataSource {
    suspend fun getStockInfo(symbols: List<String> = emptyList()): List<StockInfo>
}

class RetrofitMrNetwork @Inject constructor() : MrNetworkDataSource {

    private val baseUrl = "https://api.marketstack.com/"
    private val timeout = 10000L

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .connectTimeout(timeout, TimeUnit.MILLISECONDS)
        .readTimeout(timeout, TimeUnit.MILLISECONDS)
        .writeTimeout(timeout, TimeUnit.MILLISECONDS)
        .build()

    private val retrofitApi = trace("RetrofitApi") {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MrApiService::class.java)
    }

    override suspend fun getStockInfo(symbols: List<String>): List<StockInfo> =
        retrofitApi.getIntraDay(symbols = symbols)
}