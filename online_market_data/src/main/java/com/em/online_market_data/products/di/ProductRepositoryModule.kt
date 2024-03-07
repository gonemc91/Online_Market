package com.em.online_market_data.products.di


import com.em.online_market_data.ProductsDataRepository
import com.em.online_market_data.products.RealProductDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProductRepositoryModule {

    @Binds
    @Singleton
    fun bindProductRepository(
        productDataRepository: RealProductDataRepository
    ): ProductsDataRepository
}