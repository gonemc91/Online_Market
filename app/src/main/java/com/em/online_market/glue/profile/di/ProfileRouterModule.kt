package com.em.online_market.glue.profile.di

import com.em.online_market.glue.profile.AdapterProfileRouter
import com.em.profile.ProfileRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)

interface ProfileRouterModule {
    @Binds
    fun bindAdapterProfileRouter(
        adapterProfileRouter: AdapterProfileRouter
    ): ProfileRouter

}