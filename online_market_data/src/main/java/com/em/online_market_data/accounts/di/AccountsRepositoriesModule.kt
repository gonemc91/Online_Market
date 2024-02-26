package com.em.online_market_data.accounts.di

import com.em.online_market_data.AccountsDataRepository
import com.em.online_market_data.accounts.RealAccountsDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountRepositoriesModule {

    @Binds
    @Singleton
    fun bindAccountsRepository(
        accountsDataRepository: RealAccountsDataRepository
    ): AccountsDataRepository

}