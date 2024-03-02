package com.em.online_market.glue.profile.repositories

import com.em.online_market_data.settings.SettingDataSource
import com.example.profile.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class AdapterAuthTokenRepository @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : AuthTokenRepository {

    override suspend fun clearProfileData() {
        settingDataSource.deleteAllDataInfo()
    }
}