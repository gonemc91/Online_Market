package com.em.online_market_data.favoriites.di

import com.em.online_market_data.favoriites.sources.FavoritesDataSource
import com.em.online_market_data.favoriites.sources.InMemoryFavouritesDataSources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface FavouriteSourceModule {


    @Binds
    @Singleton
    fun bindCartSource(
        inMemoryFavouritesDataSources: InMemoryFavouritesDataSources
    ): FavoritesDataSource
}