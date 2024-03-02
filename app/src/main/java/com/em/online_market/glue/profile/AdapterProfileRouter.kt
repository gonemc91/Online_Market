package com.em.online_market.glue.profile


import com.em.online_market.navigation.GlobalNavComponentRouter
import com.em.profile.ProfileRouter
import javax.inject.Inject


class AdapterProfileRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : ProfileRouter {
    override fun launchFavouriteProductScreen() {
       /*globalNavComponentRouter.launch(R.id.favouritesFragment)*/
    }


    override fun goBack() {
        globalNavComponentRouter.pop()
    }

    override fun restartApp() {
        globalNavComponentRouter.restart()
    }
}