package com.em.online_market_data.accounts.sources

import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.AuthorizationDataEntity

interface AccountsDataSource {

    suspend fun authorization(signUpData: AuthorizationDataEntity) : AccountDataEntity

    suspend fun getAccount(): AccountDataEntity

}