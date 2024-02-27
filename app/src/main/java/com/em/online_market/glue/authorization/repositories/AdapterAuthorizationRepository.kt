package com.em.online_market.glue.authorization.repositories

import com.em.authorization.domain.entities.AuthorizationData
import com.em.authorization.domain.repositories.AuthorizationRepository
import com.em.online_market_data.AccountsDataRepository
import com.em.online_market_data.accounts.entities.AuthorizationDataEntity
import com.em.online_market_data.accounts.exceptions.AccountAlreadyExistsDataException
import javax.inject.Inject

class AdapterAuthorizationRepository @Inject constructor(
    private val accountRepository: AccountsDataRepository
): AuthorizationRepository {
    override suspend fun signUp(authorizationData: AuthorizationData) {
        try {
            accountRepository.authorization(
                AuthorizationDataEntity(
                    username = authorizationData.username,
                    surname = authorizationData.surname,
                    telephoneNumber = authorizationData.telephoneNumber
                )
            )
        }catch (e: AccountAlreadyExistsDataException){
            e.printStackTrace()
        }
    }
}