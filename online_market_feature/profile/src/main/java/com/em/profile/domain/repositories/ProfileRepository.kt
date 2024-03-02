package com.example.profile.domain.repositories

import com.em.common.Container
import com.em.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    /**
     * Listen for profile info of the current logger-in user.
     * @return infinite flow, always success; errors are delivering to [Container]
     */
    fun getProfile(): Flow<Container<Profile>>


    /**
     * Reload profile into flow returned [getProfile]
     */
    fun reload()




}