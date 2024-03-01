package com.em.catalog.domain

import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.catalog.domain.repositories.FavoritesRepository
import com.em.catalog.domain.repositories.ProductsRepository
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetProductsDetailsUseCase  @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val favoritesRepository: FavoritesRepository,
){

    /**
     * Listen for the product info by ID.
     * @return infinite flow, always success; errors are delivered to [Container]
     */

    fun getProduct(id: String): Flow<Container<ProductWithInfo>> {
        return favoritesRepository.getProductIdIdentifiersInFavorites()
            .map { container ->
                container.suspendMap{idsInCart ->
                    ProductWithInfo(
                        product = productsRepository.getProduct(id),
                        favourite = idsInCart.contains(id)
                    )
                }
            }
    }

    /**
     * Reload product flow returned by [getProduct]
     */

    fun reload(){
        favoritesRepository.reload()
    }

}