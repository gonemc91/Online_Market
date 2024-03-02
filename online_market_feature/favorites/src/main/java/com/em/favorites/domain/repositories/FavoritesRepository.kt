package com.em.favorites.domain.repositories

import com.em.common.Container
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    /**
     * Listen for all product IDs already added to the favorites.
     * @return infinite flow, always success; errors are delivered to [Container]
     */
    fun getProductIdIdentifiersInFavorites(): Flow<Container<Set<String>>>

    /**
     * Reload the flow returned
     */
    fun reload()

    /**
     * Add a new product to the favorites.
     */

    suspend fun addToFavorites(productId: String)

    /**
     * Delete product in the favorites.
     */
    suspend fun deleteToFavorites(productId: String)

}