package com.em.online_market_data.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import androidx.core.content.edit
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
        Log.d("myToken", "set token: $token")
        preference.edit {
            if (token == null) {
                remove(PREF_TOKEN)
            } else
                Log.d("myToken", "put token: $token")
                putString(PREF_TOKEN, token)
        }
    }

    override fun getToken(): String? {
        val token = preference.getString(PREF_TOKEN, null)
        Log.d("myToken", "get token: $token")
        return token
    }

    override fun listenToken(): Flow<String?> {
        return tokenFlow
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        tokenFlow.value = getToken()
    }

    companion object{
        const val PREFERENCES_NAME = "preferences"
        const val PREF_TOKEN = "token"

    }
}