package com.em.online_market.glue.catalog.repositories

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.domain.repositories.ProductsRepository
import com.em.common.Container
import com.em.online_market.glue.catalog.mappers.ProductFilterMapper
import com.em.online_market.glue.catalog.mappers.toProduct
import com.em.online_market_data.ProductsDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterProductRepository @Inject constructor(
    private val productsDataRepository: ProductsDataRepository,
    private val productFilterMapper: ProductFilterMapper,
): ProductsRepository {


    override fun getProducts(filter: ProductFilter): Flow<Container<List<Product>>> {
        val dataFilter = productFilterMapper.toProductDataFilter(filter)
        return productsDataRepository.getProducts(dataFilter).map {container->
            container.suspendMap{list ->
                list.map {dataEntity ->
                    dataEntity.toProduct()
                }
            }
        }
    }


    override suspend fun getProduct(id: String): Product {
        return productsDataRepository.getProductById(id).toProduct()
    }



    override suspend fun getAllTags(): Set<String> {
        return productsDataRepository.getAllTags()
    }
}