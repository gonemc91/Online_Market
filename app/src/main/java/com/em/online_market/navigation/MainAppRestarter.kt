package com.em.online_market.navigation

import com.em.common.AppRestarter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainAppRestarter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : AppRestarter {

    override fun restartApp() {
        globalNavComponentRouter.restart()
    }
}