package com.em.favorites.domain

import com.em.catalog.domain.repositories.FavoritesRepository
import javax.inject.Inject

class DeleteFavouritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
){

    /**
     * Delete product in favourites.
     * @throws ClassNotFoundException
     */

    suspend fun deleteFavouritesItem(productId: String){
       favoritesRepository.deleteToFavorites(productId)
    }

}