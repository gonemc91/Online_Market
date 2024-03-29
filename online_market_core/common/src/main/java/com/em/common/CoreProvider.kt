package com.em.common

import kotlinx.coroutines.CoroutineScope

/**
 * Provides a global entities for [Core] via [Core.init] method.
 */


interface CoreProvider {

    var commonUi: CommonUi

    val logger: AppLogger

    val resources: AppResources

    val globalScope: CoroutineScope

    val errorHandler: ErrorHandler

    val appRestarter: AppRestarter

    val screenCommunication: ScreenCommunication

    val remoteTimeoutMillis: Long get() = 10_000L

    val localTimeoutMillis: Long get() = 3_000L

    val debouncePeriodMillis: Long get() = 200L

}