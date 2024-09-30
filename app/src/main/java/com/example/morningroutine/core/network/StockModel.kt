package com.example.morningroutine.core.network

import java.util.Date

data class StockModel(
    val close: Any,
    val date: Date,
    val exchange: String,
    val high: Double,
    val last: Any?,
    val low: Double,
    val open: Double,
    val symbol: String,
    val volume: Any
)