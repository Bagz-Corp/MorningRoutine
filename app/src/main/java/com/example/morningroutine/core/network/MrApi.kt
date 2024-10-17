package com.example.morningroutine.core.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val ACCESS_KEY = "baf52049b27c483cbca06d0ff540ac73"

interface MrApi {
    @GET("/v1/intraday")
    suspend fun getIntraDay(
        @Query("access_key") accessKey: String = ACCESS_KEY,
        @Query("symbols") symbols: String,
        @Query("limit") limit: Int = 10
    ): Response<BaseResponse<StockModel>>
}