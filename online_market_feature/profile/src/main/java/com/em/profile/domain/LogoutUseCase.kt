package com.example.profile.domain

import com.example.profile.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authToken: AuthTokenRepository
) {

    /**
     * Logout from the app.
     */

    suspend fun logout(){
        authToken.clearProfileData()
    }
}