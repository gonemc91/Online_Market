package com.em.common.flow

import com.em.common.Core
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class DefaultLazyFlowSubjectFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
    private val cacheTimeoutMillis: Long = 1000L
) : LazyFlowSubjectFactory {

    override fun <T> create(loader: ValueLoader<T>): LazyFlowSubject<T> {
        return DefaultLazyFlowSubject(loader, dispatcher, globalScope, cacheTimeoutMillis)
    }

}