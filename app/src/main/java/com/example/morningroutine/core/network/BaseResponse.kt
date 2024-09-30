package com.example.morningroutine.core.network


data class BaseResponse<T>(
    val pagination: PaginationModel,
    val data: List<T>
)