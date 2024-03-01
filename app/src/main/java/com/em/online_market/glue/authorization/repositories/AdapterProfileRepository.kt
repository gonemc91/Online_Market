package com.em.online_market.glue.authorization.repositories

import android.util.Log
import com.em.authorization.domain.repositories.ProfileRepository
import com.em.common.AuthException
import com.em.common.unwrapFirst
import com.em.online_market_data.AccountsDataRepository
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountDataRepository: AccountsDataRepository,
): ProfileRepository {

    override suspend fun canLoadProfile(): Boolean {
        return try {
           val ac = accountDataRepository.getAccount().unwrapFirst()

            Log.d("DataAC", ac.toString())
            return true
        }catch (ignored: AuthException){
            false
        }
    }
}