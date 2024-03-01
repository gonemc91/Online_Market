package com.em.online_market.glue.authorization.di

import com.em.authorization.AuthorizationRouter
import com.em.authorization.domain.repositories.ProfileRepository
import com.em.online_market.glue.authorization.navigation.AdapterAuthorizationRouter
import com.em.online_market.glue.authorization.repositories.AdapterAuthTokenRepository
import com.em.online_market.glue.authorization.repositories.AdapterProfileRepository
import com.example.sign_in.domain.repositories.AuthTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AuthorizationRouterModule {
    @Binds
    fun bindSignUpRouter(
        router: AdapterAuthorizationRouter
    ): AuthorizationRouter


    @Binds
    fun bindAuthTokenRepository(
        authTokenRepository: AdapterAuthTokenRepository
    ): AuthTokenRepository

    @Binds
    fun bindProfileRepository(
        profileRepository: AdapterProfileRepository
    ): ProfileRepository


}