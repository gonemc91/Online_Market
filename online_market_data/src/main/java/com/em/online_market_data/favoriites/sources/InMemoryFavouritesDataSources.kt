package com.em.online_market_data.favoriites.sources

import com.em.online_market_data.favoriites.entities.FavoritesProduct
import javax.inject.Inject

class InMemoryFavouritesDataSources @Inject constructor() : FavoritesDataSource {


    private val favorites = mutableSetOf<FavoritesProduct>().toMutableSet()


    override suspend fun getFavorites(): List<FavoritesProduct> {
        return favorites.toList()
    }

    override suspend fun saveToFavorites(productId: String) {
        favorites.add(FavoritesProduct(id = productId, favorite = true))

    }

    override suspend fun delete(productId: String) {
        favorites.removeAll { it.id == productId }
    }

    override fun getFavouritesSize(): Int {
        return favorites.toList().size
    }
}