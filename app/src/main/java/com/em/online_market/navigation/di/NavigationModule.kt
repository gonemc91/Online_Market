package com.em.online_market.navigation.di

import com.em.common.AppRestarter
import com.em.common_impl.ActivityRequired
import com.em.online_market.navigation.GlobalNavComponentRouter
import com.em.online_market.navigation.MainAppRestarter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet


@Module
@InstallIn(SingletonComponent::class)

class NavigationModule {

    @Provides
    fun providesAppRestarter(
        appRestarter: MainAppRestarter
    ): AppRestarter {
        return appRestarter
    }


    @Provides
    @IntoSet
    fun provideRouterActivityRequired(
        router: GlobalNavComponentRouter,
    ): ActivityRequired {
        return router
    }

}