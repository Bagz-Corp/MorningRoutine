package com.example.morningroutine.core.network

data class PaginationModel(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int
)