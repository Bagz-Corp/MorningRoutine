package com.example.morningroutine.core.data.repository

import com.example.morningroutine.ui.routine.StockInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface StockRepository {

    fun getTimeSeriesDailyInfo(): Flow<StockInfo>
}

class StockRepositoryImpl : StockRepository {

    override fun getTimeSeriesDailyInfo(): Flow<StockInfo> = flow {
        val stockInfo = StockInfo(
            name = "",
            currentValue = 0,
            historicValues = listOf()
        )
        emit(stockInfo)
    }

}