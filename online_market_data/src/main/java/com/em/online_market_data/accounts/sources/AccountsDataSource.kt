package com.example.data.accounts.sources

import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    suspend fun authorization(signUpData: SignUpDataEntity) : AccountDataEntity

    suspend fun getAccount(): AccountDataEntity

}