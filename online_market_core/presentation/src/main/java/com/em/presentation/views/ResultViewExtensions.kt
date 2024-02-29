package com.em.presentation.views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.em.common.Container
import com.em.presentation.live.LiveValue
import com.em.presentation.observeStateOn
import kotlinx.coroutines.flow.Flow


/**
 * Observe the specified [liveData] and call [onSuccess] callback
 * if the [liveData] contains [Contains.Success] value.
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveData: LiveData<Container<T>>,
    onSuccess: (T) -> Unit,
) {
    liveData.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

/**
 * Observe the specified [flow] and call [onSuccess] callback
 * if the [flow] contains [Container.Success] value.
 */

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveValue: LiveValue<Container<T>>,
    onSuccess: (T) -> Unit,
) {
    liveValue.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

/**
 * Observe the specified [flow] and call [onSuccess] callback
 * if the [flow] contains [Container.Success] value.
 */

fun <T> ResultView.observe(
    owner: LifecycleOwner,
    flow: Flow<Container<T>>,
    onSuccess: (T) -> Unit,
) {
    flow.observeStateOn(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

