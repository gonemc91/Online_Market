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


    override fun getFavorites(): Flow<Container<List<FavoritesProduct>>> {
        favouritesSubject.newAsyncLoad(silently = true)
        return favouritesSubject.listen()
    }

    override suspend fun addToFavorites(productId: String) {
        Core.logger.log("Add to local")
        favouriteDataSource.saveToFavorites(productId)
        notifyChanges()
    }


    override suspend fun deleteFavorites(productId: String) {
        Core.logger.log("Delete to local")
        favouriteDataSource.delete(productId)
        notifyChanges()
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
    }
}