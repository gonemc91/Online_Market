package com.em.online_market_data.products

import com.em.common.ConnectionException
import com.em.common.Container
import com.em.common.entities.OnChange
import com.em.common.flow.LazyFlowSubjectFactory
import com.em.online_market_api.OnlineMarketApi
import com.em.online_market_api.models.ResponseDTO
import com.em.online_market_data.ProductsDataRepository
import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.em.online_market_data.products.sources.ProductsDataSource
import com.em.online_market_data.products.sources.mappers.mapProductDTOInDBO
import com.example.data.product.entities.ProductDataFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class RealProductDataRepository @Inject constructor(
    private val onlineMarketApi: OnlineMarketApi,
    private val productsDataSource: ProductsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory,
): ProductsDataRepository {



    private val updateNotifierFlow = MutableStateFlow(OnChange(Unit))



    override suspend fun getProductById(id: Long): ProductDBO {
        TODO()
    }

    override suspend fun getAllTags(): List<String> {
       TODO()
    }

    @ExperimentalCoroutinesApi
    override fun getProducts(filter: ProductDataFilter): Flow<Container<List<ProductDBO>>> {
        return updateNotifierFlow.flatMapLatest{
            val getProductsDTO = onlineMarketApi.getProductsList()
            if (getProductsDTO.isFailure) throw ConnectionException(Exception(getProductsDTO.exceptionOrNull()))
            else{
            delay(1000)
            lazyFlowSubjectFactory.create {
                val productDTO =
                    getProductsDTO.getOrDefault(ResponseDTO(emptyList()))
                productDTO.items.map { product ->
                    val productImages = productsDataSource.getImageProducts()
                    mapProductDTOInDBO(product, productImages)
                }
            }
            }.listen()
        }
    }
}