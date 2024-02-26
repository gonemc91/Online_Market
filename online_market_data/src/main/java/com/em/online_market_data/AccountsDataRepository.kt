package com.em.online_market_data

import com.em.common.Container
import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.SignUpDataEntity
import kotlinx.coroutines.flow.Flow


interface AccountsDataRepository {


    /**
     * Listen for the current account.
     * @return infinite flow, always success; errors are delivered to [Container]
     */

    fun getAccount(): Flow<Container<AccountDataEntity>>


    /**
     * Authorization in app.
     */
    suspend fun authorization(singUpData: SignUpDataEntity)



    fun reload()


}