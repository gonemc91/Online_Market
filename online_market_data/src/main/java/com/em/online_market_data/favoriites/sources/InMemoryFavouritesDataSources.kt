package com.em.online_market_data.favoriites.sources

import com.em.common.Core
import com.em.online_market_data.favoriites.entities.FavoritesProduct
import javax.inject.Inject

class InMemoryFavouritesDataSources @Inject constructor() : FavoritesDataSource {


    private val favorites = mutableSetOf<FavoritesProduct>().toMutableSet()


    override suspend fun getFavorites(): List<FavoritesProduct> {
        return favorites.toList()
    }

    override suspend fun saveToFavorites(productId: String) {

        Core.logger.log("Save to local $productId")
        favorites.add(FavoritesProduct(id = productId, favorite = true))

    }

    override suspend fun delete(productId: String) {

        Core.logger.log("Delete to local $productId")
        favorites.removeAll { it.id == productId }
    }
}