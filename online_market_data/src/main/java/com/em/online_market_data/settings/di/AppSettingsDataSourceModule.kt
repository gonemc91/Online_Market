package com.em.online_market_data.settings.di

import com.em.online_market_data.settings.SettingDataSource
import com.em.online_market_data.settings.SharedPreferencesSettingsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppSettingsDataSourceModule {

    @Binds
    @Singleton

    fun bindAppSettingsDataSource(
        settingsDataSource: SharedPreferencesSettingsDataSource
    ): SettingDataSource
}