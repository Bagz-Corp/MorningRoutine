package com.example.morningroutine.core.network

import androidx.compose.ui.util.trace
import com.example.morningroutine.ui.routine.StockInfo
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

interface MrNetworkDataSource {
    suspend fun getStockInfo(symbols: List<String> = emptyList()): List<StockInfo>
}

private const val MARKET_STACK_BASE_URL = "https://api.marketstack.com/"

class RetrofitMrNetwork @Inject constructor(
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : MrNetworkDataSource {

    private val networkApi = trace("RetrofitApi") {
        Retrofit.Builder()
            .baseUrl(MARKET_STACK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .build()
            .create(MrApiService::class.java)
    }

    override suspend fun getStockInfo(symbols: List<String>): List<StockInfo> =
        networkApi.getIntraDay(symbols = symbols)
}