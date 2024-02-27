package com.em.online_market.glue.authorization.navigation

import com.em.authorization.AuthorizationRouter
import com.em.online_market.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterAuthorizationRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
): AuthorizationRouter {
    override fun launch() {
        globalNavComponentRouter
    }
}