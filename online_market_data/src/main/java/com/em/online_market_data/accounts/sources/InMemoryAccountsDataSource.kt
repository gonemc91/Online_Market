package com.em.online_market_data.accounts.sources

import com.em.common.AuthException
import com.em.common.Core
import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.AuthorizationDataEntity
import com.em.online_market_data.settings.SettingDataSource
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class InMemoryAccountsDataSource @Inject constructor(
    private val settings: SettingDataSource,
) : AccountsDataSource {

    private val records = mutableListOf(
        Record(
            account = AccountDataEntity(
                id = 1,
                username = "Maрина",
                surname = "Иванова",
                telephoneNumber = "+79938774402",
                createdAtMillis = 0
            )
        )
    )
    override suspend fun authorization(signUpData: AuthorizationDataEntity): AccountDataEntity {
        delay(1000)
        val record = records.firstOrNull{ it.account.telephoneNumber == signUpData.telephoneNumber}
        return if (record != null){
            getAccount()
        }else{
            val newRecord = Record(
                account = AccountDataEntity(
                    id = records.size + 1L,
                    username = signUpData.username,
                    surname = signUpData.surname,
                    telephoneNumber = signUpData.telephoneNumber,
                    createdAtMillis = System.currentTimeMillis(),
                ),
                token = UUID.randomUUID().toString()
            )
            records.add(newRecord)
            settings.setToken(newRecord.token)
            settings.setAccountInfo(newRecord.account)
            newRecord.account
        }
    }

    override suspend fun getAccount(): AccountDataEntity {
        val token = settings.getToken()

        if (token != null) {
            val account = settings.getAccountInfo()
            records.add(
                Record(
                    account = account,
                    token = token
                )
            )
        } else
            throw AuthException()

        val record = records.firstOrNull { it.token == token } ?: throw AuthException()
        Core.logger.log("Account from Shared:  ${record.account} token:  ${record.token}")
        return record.account
    }


    private class Record(
        var account: AccountDataEntity,
        var token: String? = null,
    )

}