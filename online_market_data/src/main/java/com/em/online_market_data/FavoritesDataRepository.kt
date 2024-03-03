package com.em.online_market_data

import com.em.common.Container
import com.em.online_market_data.favoriites.entities.FavoritesProduct
import kotlinx.coroutines.flow.Flow


interface FavoritesDataRepository {

    /**
     * @return infinite flow, always success; errors are delivered to [Container]
     */
    fun getFavorites(): Flow<Container<List<FavoritesProduct>>>

    /**
     * Add a new product to the favorites.
     * @throws NotFoundException
     */
    suspend fun addToFavorites(productId: String)



    /**
     * Delete the specified favorite items.
     */
    suspend fun deleteFavorites(productId: String)


    /**
     * Reload the flow returned by [getFavorites]
     */
    fun reload()

}