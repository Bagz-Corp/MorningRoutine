package com.example.morningroutine.core.data.repository

import com.example.morningroutine.core.network.MrNetworkDataSource
import com.example.morningroutine.ui.routine.StockInfo
import javax.inject.Inject

interface StockRepository {

    /**
     * Fetch stock information about the list of symbol provided
     */
    suspend fun getIntradayInfo(symbols: List<String>): List<StockInfo>
}

class StockRepositoryImpl @Inject constructor(
    private val mrNetworkDataSource: MrNetworkDataSource
) : StockRepository {

    override suspend fun getIntradayInfo(symbols: List<String>): List<StockInfo> =
        try {
            mrNetworkDataSource.getStockInfo(symbols = symbols)
        } catch (e: Error) {
            throw e
        }

}