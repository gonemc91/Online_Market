package com.em.online_market.glue.favourites.di

import com.em.catalog.domain.repositories.FavoritesRepository
import com.em.online_market.glue.favourites.repositories.AdapterFavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

interface RepositoryModule {

    @Binds
    fun provideFavoritesRepository(
        repository: AdapterFavoritesRepository
    ): FavoritesRepository


}