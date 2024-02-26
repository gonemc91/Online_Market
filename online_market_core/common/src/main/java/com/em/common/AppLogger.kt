package com.em.common

/**
 * Global in-app logger.
 */


interface AppLogger {

    fun log(message: String)

    fun err(exception: Throwable, message: String? = null)
}