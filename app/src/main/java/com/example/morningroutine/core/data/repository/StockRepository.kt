package com.example.morningroutine.core.data.repository

import android.util.Log
import com.example.morningroutine.core.network.MrNetworkDataSource
import com.example.morningroutine.ui.routine.StockInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface StockRepository {

    /**
     * Fetch stock information about the list of symbol provided
     */
    fun getIntradayInfo(symbols: List<String>): Flow<List<StockInfo>>
}

class StockRepositoryImpl @Inject constructor(
    private val mrNetworkDataSource: MrNetworkDataSource
) : StockRepository {

    override fun getIntradayInfo(symbols: List<String>): Flow<List<StockInfo>> = flow {
        Log.i("StockRepo", "getIntradayInfo: $symbols")

        if (symbols.isEmpty()) {
            emit(emptyList())
            return@flow
        }

        mrNetworkDataSource.getStockInfo(
            symbols = symbols
        ).map {
            Log.i("StockRepo", "creating StockInfo")
            StockInfo(
                name = it.name,
                latestValue = it.latestValue,
                lastUpdateDateTime = it.lastUpdateDateTime,
                symbol = it.symbol
            )
        }.apply {
            Log.i("StockRepo", "emiting $this")
            emit(this)
        }
    }

}