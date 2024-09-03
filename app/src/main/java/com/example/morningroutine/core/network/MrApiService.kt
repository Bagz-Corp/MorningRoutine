package com.example.morningroutine.core.network

import com.example.morningroutine.ui.routine.StockInfo
import retrofit2.http.GET

interface MrApiService {

    @GET("Time Series Daily")
    suspend fun getTimeSeriesDaily(): List<StockInfo>
}