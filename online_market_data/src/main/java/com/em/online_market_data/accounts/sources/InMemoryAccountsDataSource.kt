package com.em.online_market_data.accounts.sources

import com.em.common.AuthException
import com.em.online_market_data.accounts.entities.AccountDataEntity
import com.em.online_market_data.accounts.entities.SignUpDataEntity
import com.em.online_market_data.settings.SettingDataSource
import com.example.data.accounts.sources.AccountsDataSource
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

    override suspend fun authorization(signUpData: SignUpDataEntity): AccountDataEntity {
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
            newRecord.account
        }
    }

    override suspend fun getAccount(): AccountDataEntity {
        val token = settings.getToken() ?: throw AuthException()
        val record = records.firstOrNull { it.token == token } ?: throw AuthException()
        return record.account
    }


    private class Record(
        var account: AccountDataEntity,
        var token: String? = null,
    )

}