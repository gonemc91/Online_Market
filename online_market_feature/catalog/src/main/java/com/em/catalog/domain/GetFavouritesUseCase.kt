package com.em.catalog.domain

import com.em.catalog.domain.repositories.FavoritesRepository
import com.em.common.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
){

    /**
     * Get favourites products.
     * @throws ClassNotFoundException
     */

     fun getFavouritesId(): Flow<Container<Set<String>>> {
       return favoritesRepository.getProductIdIdentifiersInFavorites()
    }

}