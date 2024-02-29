package com.em.catalog.domain

import javax.inject.Inject


class AddToFavoritesUseCase @Inject constructor(
   // private val favoritesRepository: FavoritesRepository,
) {

    /**
     * Add a new product to the favorites.
     */

   /* suspend fun addToFavorites(product: Product) = withTimeout(Core.localTimeoutMillis){
        val productInFavorites = favoritesRepository.getProduceIdentifiersInFavorites()
            .filterIsInstance<Container.Success<Set<String>>>()
            .first()
        if (!productInFavorites.value.contains(product.id)){
            favoritesRepository.addToFavorites(product.id)
        }
    }*/
}