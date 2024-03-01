package com.em.online_market_data.favoriites.sources

import com.em.online_market_data.favoriites.entities.FavoritesProduct


interface FavoritesDataSource {

    suspend fun getFavorites(): List<FavoritesProduct>

    suspend fun saveToFavorites(productId: String)

    suspend fun delete(favoritesItemId: String)


}