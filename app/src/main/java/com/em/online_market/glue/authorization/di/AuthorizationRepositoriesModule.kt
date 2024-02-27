package com.em.online_market.glue.authorization.di

import com.em.authorization.domain.repositories.AuthorizationRepository
import com.em.online_market.glue.authorization.repositories.AdapterAuthorizationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthorizationRepositoriesModule {

    @Binds
    fun bindSignUpDataSource(signUpDataSource: AdapterAuthorizationRepository): AuthorizationRepository

}