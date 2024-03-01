package com.em.online_market.glue.authorization.repositories

import com.em.online_market_data.settings.SettingDataSource
import com.example.sign_in.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AdapterAuthTokenRepository @Inject constructor(
    private val settingDataSource: SettingDataSource,
): AuthTokenRepository {
    override suspend fun setToken(token: String?) {
        settingDataSource.setToken(token)
    }

    override suspend fun getToken(): String? {
        return settingDataSource.getToken()
    }
}