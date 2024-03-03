package com.em.online_market_data.favoriites.di

import com.em.online_market_data.FavoritesDataRepository
import com.em.online_market_data.favoriites.RealFavoritesDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    @Singleton
    fun bindFavouritesRepository(
        productDataRepository: RealFavoritesDataRepository
    ): FavoritesDataRepository


}