package com.em.catalog.domain.repositories
import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.Product
import com.em.common.Container
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    /**
     * Listen for all products which match the specified filter.
     * @return infinite flow, always success; errors are delivered to [Container]
     */

    fun getProducts(filter: ProductFilter): Flow<Container<List<Product>>>

    /**
     * Get the product info by ID.
     */

    suspend fun getProduct(id: String): Product

    /**
     * Get min possible product price.
     */

    suspend fun getAllCategories(): List<String>

}