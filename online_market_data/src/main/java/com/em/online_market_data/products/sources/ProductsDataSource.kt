package com.em.online_market_data.products.sources

import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.em.online_market_data.products.entites.product_models.ProductImagesDBO
import com.example.data.product.entities.ProductDataFilter

interface ProductsDataSource {
    suspend fun getImageProducts():  Map<String, ProductImagesDBO>
    suspend fun mapDataToLocalStorage(productDBO: ProductDBO)

    suspend fun getProductDBOWithFilter(filter: ProductDataFilter): List<ProductDBO>

    suspend fun getTags(): List<String>

}