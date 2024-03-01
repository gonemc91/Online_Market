package com.em.authorization.domain

import com.em.authorization.domain.repositories.ProfileRepository
import com.em.common.Core
import com.example.sign_in.domain.repositories.AuthTokenRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val profileRepository: ProfileRepository,
) {
    /**
     * Whether the user is signed-in or not.
     */

    suspend fun isSignedIn(): Boolean{
        Core.logger.log("Start ready to auth")
        return authTokenRepository.getToken() != null
                && profileRepository.canLoadProfile()
    }

}