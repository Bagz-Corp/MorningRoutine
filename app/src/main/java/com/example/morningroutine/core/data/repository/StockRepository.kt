package com.example.morningroutine.core.data.repository

import android.util.Log
import com.example.morningroutine.core.network.MrNetworkDataSource
import com.example.morningroutine.ui.routine.StockInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface StockRepository {

    /**
     * Fetch stock information about the list of symbol provided
     */
    suspend fun getLatestValues(symbols: List<String>): List<StockInfo>

    fun getHistoricalValues(symbol : String): Flow<List<Float>>
}

class StockRepositoryImpl @Inject constructor(
    private val mrNetworkDataSource: MrNetworkDataSource
) : StockRepository {

    override suspend fun getLatestValues(symbols: List<String>): List<StockInfo> =
        try {
            // mrNetworkDataSource.getStockInfo(symbols = symbols)
            delay(2000)
            listOf(
                StockInfo(
                    name = "GOOGL",
                    latestValue = "165.19"
                ),
                StockInfo(
                    name = "AAPL",
                )
            )
        } catch (e: Error) {
            throw e
        }

    override fun getHistoricalValues(symbol: String): Flow<List<Float>> {
        try {
            Log.i("StockRepositoryImpl", "Fetching historical values")
//            val values = mrNetworkDataSource.getHistoricalValues(symbol = symbol).also {
//                Log.i("StockRepositoryImpl", "Historical values retrieved: $it")
//            }
            // delay(2000)
            return flowOf(listOf(230.57f, 230.76f, 235.86f, 236.48f, 235.0f, 231.78f, 233.85f))
        } catch (e: Error) {
            throw e
        }
    }
}
