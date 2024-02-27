package com.em.authorization.domain

import com.em.authorization.domain.entities.AuthorizationData
import com.em.authorization.domain.entities.AuthorizationFields
import com.em.authorization.domain.exceptions.EmptyFieldException
import com.em.authorization.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val signUpRepository: AuthorizationRepository
){

    /**
     * Crate a new account
     * @throws EmptyFieldException
     */

    suspend fun authorization(signUpData: AuthorizationData){
        if (signUpData.username.isBlank()) throw EmptyFieldException(AuthorizationFields.USERNAME)
        if (signUpData.surname.isBlank()) throw  EmptyFieldException(AuthorizationFields.SURNAME)
        if (signUpData.telephoneNumber.isBlank()) throw  EmptyFieldException(AuthorizationFields.TELEPHONE_NUMBER)

        signUpRepository.signUp(signUpData)
    }
}