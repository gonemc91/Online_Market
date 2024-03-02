package com.example.profile.domain

import com.em.common.Container
import com.em.profile.domain.entities.Profile
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    /**
     * Listen for a profile into the current logged-in user.
     * @return infinite flow, always success; errors are delivering to [Container]
     */
    fun getProfile(): Flow<Container<Profile>>{
       return profileRepository.getProfile()
    }

    /**
     * Reload profile into flow returned [getProfile]
     */
    fun reload(){
        profileRepository.reload()
    }


}