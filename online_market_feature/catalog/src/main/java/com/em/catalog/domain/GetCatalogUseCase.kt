package com.em.catalog.domain

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.Product
import com.em.catalog.domain.repositories.ProductsRepository
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetCatalogUseCase  @Inject constructor(
    private val productsRepository: ProductsRepository,
    //private val favoritesRepository: FavoritesRepositoryCatalog,
) {

    /**
     * Listen for all products which match the specified filter.
     * @return infinite flow, always success; errors are delivered to [Container]
     */


    fun getProducts(filter: ProductFilter = ProductFilter.EMPTY): Flow<Container<List<Product>>> {
        return combine(
            productsRepository.getProducts(filter),
            MutableStateFlow(null)
        ) { productsContainer, idsInCartContainer ->
            if (productsContainer !is Container.Success) return@combine productsContainer.map()
            val products = productsContainer.value
            return@combine Container.Success(products)
        }
    }
}
