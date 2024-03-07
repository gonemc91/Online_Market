package com.em.online_market_data.products.di

import com.em.online_market_data.products.sources.InMemoryProductDataSource
import com.em.online_market_data.products.sources.ProductsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductSourceModule {

    @Binds
    @Singleton
    fun bindProductSource(
        productDataSource: InMemoryProductDataSource
    ): ProductsDataSource



}