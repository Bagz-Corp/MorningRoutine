package com.example.morningroutine.ui.routine

import kotlinx.serialization.Serializable
import java.util.Date

data class StockInfo(
    val name: String = "default",
    val latestValue: String = "",
    val lastUpdateDateTime: Date = Date(),
    val symbol: String = "default",
)