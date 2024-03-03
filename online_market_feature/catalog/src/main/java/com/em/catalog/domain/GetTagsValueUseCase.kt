package com.em.catalog.domain

import com.em.catalog.domain.repositories.ProductsRepository
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsValueUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {

    /**
     * Get all available product tags.
     */

   fun getAllTags(): Flow<Container<Set<String>>> {
       return productsRepository.getAllTags()
    }

}