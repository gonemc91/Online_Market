package com.em.common


/**
 * Default global error handler for actions executed usually via viewModelScope.
 */
interface ErrorHandler {

    fun handlerError(exception: Throwable)

    fun getUserMessage(exception: Throwable): String
}