package com.em.online_market.glue.profile.repositories

import com.em.common.Container
import com.em.online_market_data.FavoritesDataRepository
import com.em.profile.domain.repositories.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdapterFavouritesItemRepository @Inject constructor(
    private val favouritesDataRepository: FavoritesDataRepository
) : FavouritesRepository {
    override fun getFavouritesItem(): Flow<Container<Int>> {
       return favouritesDataRepository.getAvailableFavouritesProducts()
    }

}