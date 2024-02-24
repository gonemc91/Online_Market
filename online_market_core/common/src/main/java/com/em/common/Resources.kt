package com.em.common

/**
 * Get string resources without direct context dependency.
 */

interface Resources {

    fun getString(id: Int): String

    fun getString(id: Int, vararg placeholder: Any): String
}