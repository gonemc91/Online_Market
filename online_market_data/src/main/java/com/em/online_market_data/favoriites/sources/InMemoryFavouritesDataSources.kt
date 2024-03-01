package com.em.online_market_data.favoriites.sources

import com.em.online_market_data.favoriites.entities.FavoritesProduct
import javax.inject.Inject

class InMemoryFavouritesDataSources @Inject constructor() : FavoritesDataSource {


    private val favorites = mutableListOf<FavoritesProduct>()


    override suspend fun getFavorites(): List<FavoritesProduct> {
        favorites.add(FavoritesProduct
            (id="54a876a5-2205-48ba-9498-cfecff4baa6e", favorite=true))
        return favorites
    }

    override suspend fun saveToFavorites(productId: String) {
        favorites.add(FavoritesProduct(id = productId, favorite = true))

    }

    override suspend fun delete(favoritesItemId: String) {
        favorites.removeAll { it.id == favoritesItemId }
    }
}