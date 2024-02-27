package com.em.authorization.domain.repositories

import com.em.authorization.domain.entities.AuthorizationData


interface AuthorizationRepository {
    suspend fun signUp(authorizationData: AuthorizationData)
}