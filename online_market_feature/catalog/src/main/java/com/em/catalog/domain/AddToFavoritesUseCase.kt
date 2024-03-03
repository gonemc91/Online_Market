package com.em.catalog.domain

import com.em.catalog.domain.repositories.FavoritesRepositoryCatalog
import com.em.common.Container
import com.em.common.Core
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


class AddToFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepositoryCatalog,
) {

    /**
     * Add a new product to the favorites.
     */
   suspend fun addToFavorites(productId: String) = withTimeout(Core.localTimeoutMillis){
        val productInFavorites = favoritesRepository.getProductIdIdentifiersInFavorites()
            .filterIsInstance<Container.Success<Set<String>>>()
            .first()
        if (!productInFavorites.value.contains(productId)){
            favoritesRepository.addToFavorites(productId)
        }
    }
}