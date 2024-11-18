package com.example.morningroutine.core.network

import com.example.morningroutine.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val ACCESS_KEY = BuildConfig.MARKETSTACK_API_KEY

interface MrApi {
    @GET("/v1/intraday")
    suspend fun getIntraDay(
        @Query("access_key") accessKey: String = ACCESS_KEY,
        @Query("symbols") symbol: String,
        @Query("interval") interval: String = "24hour",
        @Query("limit") limit: Int = 7
    ): Response<BaseResponse<StockModel>>


    @GET("/v1/intraday/latest")
    suspend fun getLatest(
        @Query("access_key") accessKey: String = ACCESS_KEY,
        @Query("symbols") symbols: String,
    ): Response<BaseResponse<StockModel>>
}