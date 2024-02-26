package com.em.common_impl

import android.content.Context
import com.em.common.AppResources

/**
 * Default implementation of [AppResources] which fetches strings from application context.
 */

class AndroidResources (
    private val applicationContext: Context
): AppResources {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, vararg placeholder: Any): String {
        return applicationContext.getString(id, placeholder)
    }
}