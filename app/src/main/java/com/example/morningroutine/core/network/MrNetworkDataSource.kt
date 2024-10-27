package com.example.morningroutine.core.network

import androidx.compose.ui.util.trace
import com.example.morningroutine.ui.routine.StockInfo
import com.google.gson.GsonBuilder
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

interface MrNetworkDataSource {
    @Throws(Error::class)
    suspend fun getStockInfo(symbols: List<String> = emptyList()): List<StockInfo>

    suspend fun getHistoricalValues(
        symbol: String
    ): List<Float>
}

private const val MARKET_STACK_BASE_URL = "https://api.marketstack.com/"

class RetrofitMrNetwork @Inject constructor(
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : MrNetworkDataSource {

    private val marketStackApi = trace("RetrofitApi") {
        Retrofit.Builder()
            .baseUrl(MARKET_STACK_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create()
                )
            )
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .build()
            .create(MrApi::class.java)
    }

    override suspend fun getStockInfo(symbols: List<String>): List<StockInfo> {
        val ret = marketStackApi.getLatest(
            symbols = symbols.joinToString(separator = ","),
            )

        if (ret.isSuccessful) {
            return ret.body()?.data?.map {
                StockInfo(
                    name = it.symbol,
                    symbol = it.symbol,
                    lastUpdateDateTime = it.date,
                    latestValue = it.last.toString()
                )
            } ?: emptyList()
        }

        throw Error(ret.message())
    }

    override suspend fun getHistoricalValues(symbol: String): List<Float> =
        with(marketStackApi.getIntraDay(symbol = symbol)) {
            if (isSuccessful) {
                return body()?.data?.map {
                    it.last.toString().toFloat()
                } ?: emptyList()
            } else {
                throw Error(message())
            }
        }
}