package com.em.online_market.glue.catalog.di

import com.em.catalog.domain.repositories.FavoritesRepositoryCatalog
import com.em.catalog.domain.repositories.ProductsRepository
import com.em.online_market.glue.catalog.repositories.AdapterFavoritesRepository
import com.em.online_market.glue.catalog.repositories.AdapterProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

interface RepositoryModule {

    @Binds
    fun provideProductRepository(
        repository: AdapterProductRepository
    ): ProductsRepository


    @Binds
    fun provideFavoritesRepository(
        repository: AdapterFavoritesRepository
    ): FavoritesRepositoryCatalog



}