package com.example.morningroutine.di

import com.example.morningroutine.core.data.repository.DataStoreRepository
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.StockRepositoryImpl
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.core.network.MrNetworkDataSource
import com.example.morningroutine.core.network.RetrofitMrNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindNetworkDataSource(
        retrofitMrNetwork: RetrofitMrNetwork
    ): MrNetworkDataSource

    @Binds
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    abstract fun bindUserPreferencesRepository(
        dataStoreRepository: DataStoreRepository
    ): UserPreferencesRepository

}
