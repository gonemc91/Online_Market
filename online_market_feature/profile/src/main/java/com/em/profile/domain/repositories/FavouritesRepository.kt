package com.em.profile.domain.repositories

import com.em.common.Container
import kotlinx.coroutines.flow.Flow

interface  FavouritesRepository {


     fun getFavouritesItem(): Flow<Container<Int>>


}