package com.em.online_market.glue.profile.repositories

import com.em.common.Container
import com.em.online_market_data.AccountsDataRepository
import com.em.profile.domain.entities.Profile
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
): ProfileRepository {

    override fun getProfile(): Flow<Container<Profile>> {
       return accountsDataRepository.getAccount().map {container->
           container.map{
              Profile(
                  id = it.id,
                  username = it.username,
                  surname = it.surname,
                  telephoneNumber = it.telephoneNumber,
                  createdAtMillis =  it.createdAtMillis,
              )
           }
       }
    }

    override fun reload() {
        accountsDataRepository.reload()
    }


}