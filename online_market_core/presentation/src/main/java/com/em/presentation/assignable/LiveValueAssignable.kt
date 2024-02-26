package com.em.presentation.assignable

import com.em.presentation.live.LiveValue
import com.em.presentation.live.MutableLiveValue

internal class LiveValueAssignable<T>(
    private val liveValue: LiveValue<T>
): Assignable<T> {
    override fun setValue(value: T) {
        (liveValue as? MutableLiveValue<T>)?.setValue(value)
    }
}