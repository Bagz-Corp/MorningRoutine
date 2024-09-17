package com.example.morningroutine.di

import com.example.morningroutine.core.network.MrNetworkDataSource
import com.example.morningroutine.core.network.RetrofitMrNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    abstract fun bindMrNetworkDataSource(
        retrofitNetwork: RetrofitMrNetwork
    ): MrNetworkDataSource

}