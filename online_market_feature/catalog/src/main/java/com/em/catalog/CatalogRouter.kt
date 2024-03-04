package com.em.catalog

import com.em.catalog.domain.entitys.product.Product

interface CatalogRouter {

    /**
     * Launch product details screen.
     */
    fun launchDetails(productId: Product)



}