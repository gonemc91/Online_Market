package com.example.common_ipl

import android.content.Context
import com.em.common.AppRestarter
import com.em.common.CommonUi
import com.em.common.CoreProvider
import com.em.common.ErrorHandler
import com.em.common.Logger
import com.em.common.Resources
import com.em.common.ScreenCommunication
import com.em.common_impl.AndroidCommonUi
import com.em.common_impl.AndroidLogger
import com.em.common_impl.AndroidResources
import com.em.common_impl.DefaultErrorHandler
import com.em.common_impl.DefaultScreenCommunication
import com.em.common_impl.createDefaultScope
import kotlinx.coroutines.CoroutineScope

class DefaultCoreProvider(
    private val appContext: Context,
    override val appRestarter: AppRestarter,
    override var commonUi: CommonUi = AndroidCommonUi(appContext),
    override val resources: Resources = AndroidResources(appContext),
    override val screenCommunication: ScreenCommunication = DefaultScreenCommunication(),
    override val globalScope: CoroutineScope = createDefaultScope(),
    override val logger: Logger = AndroidLogger(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        logger, commonUi, resources, appRestarter, globalScope
    )
) : CoreProvider

