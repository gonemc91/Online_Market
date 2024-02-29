package com.em.online_market_data

import com.em.common.Container
import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.example.data.product.entities.ProductDataFilter
import kotlinx.coroutines.flow.Flow

interface ProductsDataRepository {

    /**
     * Listen for products that match the specified filter.
     * @retutn infinite flow, always success; errors are delivered to [Container]
     */

    fun getProducts(filter: ProductDataFilter): Flow<Container<List<ProductDBO>>>



    /**
     * Get the product ID
     * @throws NotFountException
     */

    suspend fun getProductById(id: String): ProductDBO



    /**
     * Get all available categories.
     */

    suspend fun getAllTags(): List<String>

}