package com.em.catalog.domain.repositories

import com.em.common.Container
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    /**
     * Listen for all product IDs already added to the favorites.
     * @return infinite flow, always success; errors are delivered to [Container]
     */
    fun getProduceIdentifiersInFavorites(): Flow<Container<Set<String>>>

    /**
     * Reload the flow returned by [getProduceIdentifiersInCart]
     */
    fun reload()

    /**
     * Add a new product to the cart.
     */

    suspend fun addToFavorites(productId: String)

}