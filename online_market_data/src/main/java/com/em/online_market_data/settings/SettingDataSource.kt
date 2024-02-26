package com.em.online_market_data.settings

import kotlinx.coroutines.flow.Flow

interface SettingDataSource {

     /**
      * Save auth token
      */

     fun setToken(token: String?)


     /**
      * Get thr current auth token
      */

     fun getToken(): String?

     /**
      * Listen for the current auth token
      */

     fun listenToken(): Flow<String?>
 }