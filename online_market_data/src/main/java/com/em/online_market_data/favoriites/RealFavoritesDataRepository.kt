package com.em.online_market_data.favoriites

import com.em.common.Container
import com.em.common.Core
import com.em.common.flow.LazyFlowSubjectFactory
import com.em.online_market_data.FavoritesDataRepository
import com.em.online_market_data.favoriites.entities.FavoritesProduct
import com.em.online_market_data.favoriites.sources.FavoritesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealFavoritesDataRepository @Inject constructor (
    private val favouriteDataSource: FavoritesDataSource,
    lazyFlowSubjectFactory: LazyFlowSubjectFactory,
) : FavoritesDataRepository {



    private val favouritesSubject = lazyFlowSubjectFactory.create {
        favouriteDataSource.getFavorites()
    }


    private val favouritesSize = lazyFlowSubjectFactory.create {
        favouriteDataSource.getFavouritesSize()
    }


    override fun getFavorites(): Flow<Container<List<FavoritesProduct>>> {
        favouritesSubject.newAsyncLoad(silently = true)
        return favouritesSubject.listen()
    }

    override suspend fun addToFavorites(productId: String) {
        favouriteDataSource.saveToFavorites(productId)
        notifyChanges()
    }


    override suspend fun deleteFavorites(productId: String) {
        favouriteDataSource.delete(productId)
        notifyChanges()
    }

    override fun getAvailableFavouritesProducts(): Flow<Container<Int>> {
        favouritesSize.newAsyncLoad(silently = true)
        return favouritesSize.listen()
    }

    override fun reload() {
        favouritesSubject.newAsyncLoad()
    }


    private suspend fun notifyChanges(){
        val get = favouriteDataSource.getFavorites()
        get.forEach {
            Core.logger.log("Data favourites download ${it}")
        }
        favouritesSubject.updateWith(Container.Success(favouriteDataSource.getFavorites()))
        favouritesSize.updateWith(Container.Success(favouriteDataSource.getFavouritesSize()))

    }
}