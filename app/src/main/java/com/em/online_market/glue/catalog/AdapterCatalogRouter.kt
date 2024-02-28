package com.em.online_market.glue.catalog

import com.em.catalog.CatalogRouter
import com.em.online_market.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCatalogRouter @Inject constructor(
    private val  globalNavComponentRouter: GlobalNavComponentRouter,
): CatalogRouter {
    override fun launchDetails(productId: Long) {
        TODO()
      /*  globalNavComponentRouter.launch(R.id.productDetailsFragment,
            ProductDetailsFragment.Screen(productId))*/
    }

}