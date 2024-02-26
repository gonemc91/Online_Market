package com.em.presentation.live

import androidx.lifecycle.LifecycleOwner


/**
 * Wrapper for value thant can be observed with the specified lifecycle.
 * Usually used as a replacement of [LiveData] in combination [BaseViewModel]
 * to avoid LiveData duplication, E.g. you can write this:
 *
 * ```
 * val userLiveValue = liveValue<User>()
 * ```
 *
 * instead of this:
 *
 * ```
 * private val _userLiveData = mutableLiveData<User>()
 * val userLiveDat: LiveData<User> = _userLiveData
 * ```
 *
 */
interface LiveValue<T> {

    fun observe(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit)

    fun requireValue(): T

    fun getValue(): T?

}