package com.em.online_market_data.products.sources

import com.em.online_market_data.products.entites.product_models.ProductImagesDBO

interface ProductsDataSource {
    suspend fun getImageProducts():  Map<String, ProductImagesDBO>

}