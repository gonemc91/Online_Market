package com.em.online_market_data.accounts

import com.em.common.AuthException
import com.em.common.Container
import com.em.common.flow.LazyFlowSubjectFactory
import com.em.online_market_data.AccountsDataRepository
import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.SignUpDataEntity
import com.em.online_market_data.settings.SettingDataSource
import com.example.data.accounts.sources.AccountsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealAccountsDataRepository @Inject constructor(
    private val accountsDataSource: AccountsDataSource,
    settingsDataSource: SettingDataSource,
    coroutineScope: CoroutineScope,
    lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : AccountsDataRepository {

    private val accountLazyFlowSubject = lazyFlowSubjectFactory.create {
        accountsDataSource.getAccount()
    }

    init {
        coroutineScope.launch {
            settingsDataSource.listenToken().collect{
                if(it != null){
                    accountLazyFlowSubject.newAsyncLoad(silently = true)
                }else{
                    accountLazyFlowSubject.updateWith(Container.Error(AuthException()))
                }
            }
        }

    }
    override fun getAccount(): Flow<Container<AccountDataEntity>> {
        return  accountLazyFlowSubject.listen()
    }

    override suspend fun authorization(singUpData: SignUpDataEntity){
        accountsDataSource.authorization(singUpData)
    }

    override fun reload() {
        accountLazyFlowSubject.newAsyncLoad()
    }
}