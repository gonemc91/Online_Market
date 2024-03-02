package com.em.favorites.domain

import com.em.common.Container
import com.em.favorites.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class AddToFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {

    /**
     * Add a new product to the favorites.
     */
   suspend fun addToFavorites(productId: String){
        val productInFavorites = favoritesRepository.getProductIdIdentifiersInFavorites()
            .filterIsInstance<Container.Success<Set<String>>>()
            .first()
        if (!productInFavorites.value.contains(productId)){
            favoritesRepository.addToFavorites(productId)
        }
    }
}