package com.em.online_market.glue.catalog

import com.em.catalog.CatalogRouter
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.presentation.details.ProductDetailsFragment
import com.em.online_market.R
import com.em.online_market.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterCatalogRouter @Inject constructor(
    private val  globalNavComponentRouter: GlobalNavComponentRouter,
): CatalogRouter {
    override fun launchDetails(product: Product) {
     globalNavComponentRouter.launch(R.id.productDetailsFragment,
            ProductDetailsFragment.Screen(product))
    }

}