package com.em.online_market_data.products

import com.em.api.OnlineMarketApi
import com.em.common.Container
import com.em.common.entities.OnChange
import com.em.common.flow.LazyFlowSubjectFactory
import com.em.online_market_data.ProductsDataRepository
import com.em.online_market_data.products.entites.product_models.ProductDBO
import com.em.online_market_data.products.sources.ProductsDataSource
import com.em.online_market_data.products.sources.mappers.mapProductDTOInDBO
import com.example.data.product.entities.ProductDataFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
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


    private val tagsSubject = lazyFlowSubjectFactory.create {
        productsDataSource.getAllTags()
    }



    override suspend fun getProductById(id: String): ProductDBO {
       return productsDataSource.getProductById(id)
    }

    override fun getAllTags():  Flow<Container<Set<String>>> {
           return tagsSubject.listen()
    }


    fun reload() {
            tagsSubject.newAsyncLoad(silently = true)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getProducts(filter: ProductDataFilter): Flow<Container<List<ProductDBO>>> {
        return updateNotifierFlow.flatMapLatest{
            val getProductsDTO = onlineMarketApi.getProductsList()
            getProductsDTO.items.map {product ->
                val productImages = productsDataSource.getImageProducts()
                productsDataSource.mapDataToLocalStorage(mapProductDTOInDBO(product, productImages))
            }
            reload()
            lazyFlowSubjectFactory.create {
                productsDataSource.getProductDBOWithFilter(filter)
            }.listen()
        }
    }
}