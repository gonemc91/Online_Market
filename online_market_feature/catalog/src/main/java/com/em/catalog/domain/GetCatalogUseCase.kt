package com.em.catalog.domain

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.ProductWithCartInfo
import com.em.catalog.domain.repositories.ProductsRepository
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetCatalogUseCase  @Inject constructor(
    private val productsRepository: ProductsRepository,
    //private val favoritesRepository: FavoritesRepository,
) {

    /**
     * Listen for all products which match the specified filter.
     * @return infinite flow, always success; errors are delivered to [Container]
     */

    /*    fun getProducts(filter: ProductFilter): Flow<Container<List<ProductWithCartInfo>>> {
        return combine(
            productsRepository.getProducts(filter),
            favoritesRepository.getProduceIdentifiersInFavorites()
        ){productsContainer, idsInCartContainer ->
            if(productsContainer !is Container.Success) return@combine productsContainer.map()
            if(idsInCartContainer !is Container.Success) return@combine idsInCartContainer.map()
            val products = productsContainer.value
            val idsInFavorites = idsInCartContainer.value
            val productsWithCartInfo = products.map { ProductWithCartInfo(it, idsInFavorites.contains(it.id)) }
            return@combine Container.Success(productsWithCartInfo)
        }
    }*/

    fun getProducts(filter: ProductFilter): Flow<Container<List<ProductWithCartInfo>>> {
        return combine(
            productsRepository.getProducts(filter),
            MutableStateFlow(null)
        ) { productsContainer, idsInCartContainer ->
            if (productsContainer !is Container.Success) return@combine productsContainer.map()
            val products = productsContainer.value
            val idsInFavorites = false
            val productsWithCartInfo = products.map { ProductWithCartInfo(it, false) }
            return@combine Container.Success(productsWithCartInfo)
        }
    }
}
