package com.em.catalog

import com.em.catalog.domain.entitys.product.ProductWithInfo

interface CatalogRouter {

    /**
     * Launch product details screen.
     */
    fun launchDetails(productId: ProductWithInfo)


}