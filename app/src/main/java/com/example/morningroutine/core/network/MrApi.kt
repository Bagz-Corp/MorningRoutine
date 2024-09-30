package com.example.morningroutine.core.network

import com.example.morningroutine.model.JsonPlaceholderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val ACCESS_KEY = "3e1aa6e455dbf3709d3edb22a98d2901"

interface MrApi {
    @GET("/v1/intraday")
    suspend fun getIntraDay(
        @Query("access_key") accessKey: String = ACCESS_KEY,
        @Query("symbols") symbols: String,
        @Query("limit") limit: Int = 10
    ): Response<BaseResponse<StockModel>>
}