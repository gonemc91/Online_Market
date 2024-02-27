package com.em.online_market.glue.authorization.di

import com.em.authorization.AuthorizationRouter
import com.em.online_market.glue.authorization.navigation.AdapterAuthorizationRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthorizationRouterModule {
    @Binds
    fun bindSignUpRouter(router: AdapterAuthorizationRouter): AuthorizationRouter
}