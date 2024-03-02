package com.em.catalog.domain.repositories

import javax.inject.Inject

class GetFilterValueUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {

    /**
     * Get all available product categories.
     */

    suspend fun getCategories(): List<String>{
        return productsRepository.getAllCategories()
    }

}