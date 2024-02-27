package com.em.authorization.domain.exceptions

import com.em.authorization.domain.entities.AuthorizationFields
import com.em.common.AppException

class EmptyFieldException (
    val field: AuthorizationFields,
): AppException()