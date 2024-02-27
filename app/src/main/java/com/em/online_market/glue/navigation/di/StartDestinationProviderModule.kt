package com.em.online_market.glue.navigation.di

import com.em.online_market.glue.navigation.DefaultDestinationProvider
import com.em.online_market.navigation.DestinationsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StartDestinationProviderModule {

    @Binds
    fun bindStartDestinationProvider(
        startDestinationProvider: DefaultDestinationProvider
    ): DestinationsProvider

}