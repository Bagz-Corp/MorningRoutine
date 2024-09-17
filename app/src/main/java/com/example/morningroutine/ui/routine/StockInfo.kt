package com.example.morningroutine.ui.routine

import java.util.Date

data class StockInfo(
    val name: String = "default",
    val latestValue: Int = 0,
    val lastUpdateDateTime: Date = Date(),
    val symbol: String = "default"
)