package com.example.morningroutine.ui.routine

data class StockInfo(
    val name: String,
    val currentValue: Int,
    val historicValues: List<Int>, // Daily historic values
)