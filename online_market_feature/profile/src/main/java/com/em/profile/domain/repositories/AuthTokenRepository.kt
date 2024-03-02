package com.example.profile.domain.repositories

interface AuthTokenRepository {

    /**
     * Clear auth data saved in app.
     */

    suspend fun clearProfileData()



}