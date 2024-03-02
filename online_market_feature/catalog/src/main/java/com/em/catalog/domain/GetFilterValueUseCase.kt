package com.em.catalog.domain

import com.em.catalog.domain.repositories.ProductsRepository
import javax.inject.Inject

class GetFilterValueUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {

    /**
     * Get all available product tags.
     */

    suspend fun getAllTags(): Set<String>{
        return productsRepository.getAllTags()
    }

}