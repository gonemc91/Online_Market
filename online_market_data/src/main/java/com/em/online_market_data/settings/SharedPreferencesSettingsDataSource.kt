package com.em.online_market_data.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import com.em.online_market_data.accounts.entities.AccountDataEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedPreferencesSettingsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingDataSource, OnSharedPreferenceChangeListener {

    private val preference = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )
    private val  tokenFlow = MutableStateFlow<String?>(null)

    init {
        tokenFlow.value = getToken()
        preference.registerOnSharedPreferenceChangeListener(this)
    }



    @SuppressLint("SuspiciousIndentation")
    override fun setToken(token: String?) {
        preference.edit {
            if (token == null) {
                remove(PREF_TOKEN)
            } else
                putString(PREF_TOKEN, token)
        }
    }

    override fun getToken(): String? {
        val token = preference.getString(PREF_TOKEN, null)
        return token
    }

    override fun listenToken(): Flow<String?> {
        return tokenFlow
    }

    override fun setAccountInfo(accountDataEntity: AccountDataEntity) {
        preference.edit{
            putLong(PREF_USER_ID,accountDataEntity.id)
            putString(PREF_USERNAME,accountDataEntity.username)
            putString(PREF_SURNAME,accountDataEntity.surname)
            putString(PREF_TEL_NUMBER,accountDataEntity.telephoneNumber)
            putLong(PREF_DATE_CREATE, accountDataEntity.createdAtMillis)
        }
    }


    override fun getAccountInfo(): AccountDataEntity {
        return AccountDataEntity(
            id = preference.getLong(PREF_USER_ID, 0),
            username = preference.getString(PREF_USERNAME, NO_DATA) ?: NO_DATA,
            surname = preference.getString(PREF_SURNAME, NO_DATA) ?: NO_DATA,
            telephoneNumber = preference.getString(PREF_TEL_NUMBER, NO_DATA) ?: NO_DATA,
            createdAtMillis = preference.getLong(PREF_DATE_CREATE, 0)
        )
    }

    override fun deleteAllDataInfo() {
        preference.edit().clear().apply()
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        tokenFlow.value = getToken()
    }

    companion object{
        const val PREFERENCES_NAME = "preferences"
        const val PREF_TOKEN = "token"
        const val PREF_USER_ID = "user_id"
        const val PREF_USERNAME = "username"
        const val PREF_SURNAME = "surname"
        const val PREF_TEL_NUMBER = "tel_number"
        const val PREF_DATE_CREATE = "create_at"
        const val NO_DATA = "NO DATA"



    }
}