package com.em.favorites.domain

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.em.catalog.domain.entitys.product.ProductWithInfo
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesProductsUseCase @Inject constructor(

){

    /**
     * Get favourites products.
     * @throws ClassNotFoundException
     */

     fun getFavouritesProducts(filter: ProductFilter): Flow<Container<List<ProductWithInfo>>> {
      TODO()
    }

}