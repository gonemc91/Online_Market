package com.em.common_impl

import androidx.fragment.app.FragmentActivity

/**
 * This interface indicates the implementation needs te be aware of
 * activity lifecycle.
 */


interface ActivityRequired {

    fun onCreated(activity: FragmentActivity)

    fun onStarted()

    fun onStopped()

    fun onDestroyed()

}