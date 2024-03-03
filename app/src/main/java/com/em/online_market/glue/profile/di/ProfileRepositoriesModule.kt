package com.em.online_market.glue.profile.di

import com.em.online_market.glue.profile.repositories.AdapterAuthTokenRepository
import com.em.online_market.glue.profile.repositories.AdapterFavouritesItemRepository
import com.em.online_market.glue.profile.repositories.AdapterProfileRepository
import com.em.profile.domain.repositories.FavouritesRepository
import com.example.profile.domain.repositories.AuthTokenRepository
import com.example.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoriesModule {


    @Binds
    fun bindAuthTokenRepository(
        adapterAuthTokenRepository: AdapterAuthTokenRepository
    ): AuthTokenRepository



    @Binds
    fun bindProfileRepository(
        adapterProfileRepository: AdapterProfileRepository
    ): ProfileRepository




    @Binds
    fun bindFavouritesItemRepository(
        adapterFavouritesRepository: AdapterFavouritesItemRepository
    ): FavouritesRepository
}