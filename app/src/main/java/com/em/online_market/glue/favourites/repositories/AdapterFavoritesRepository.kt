package com.em.online_market.glue.favourites.repositories

import com.em.catalog.domain.repositories.FavoritesRepositoryCatalog
import com.em.common.Container
import com.em.online_market_data.FavoritesDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterFavoritesRepository @Inject constructor(
    private val favoritesRepository: FavoritesDataRepository,
): FavoritesRepositoryCatalog {

    override fun getProductIdIdentifiersInFavorites(): Flow<Container<Set<String>>> {
        return favoritesRepository.getFavorites().map { container ->
            container.map{list ->
                list.map {
                    it.id
                }.toSet()
            }
        }
    }

    override fun reload() {
        favoritesRepository.reload()
    }

    override suspend fun addToFavorites(productId: String) {
        favoritesRepository.addToFavorites(productId)
    }

    override suspend fun deleteToFavorites(productId: String) {
        favoritesRepository.deleteFavoritesItem(productId)
    }


}