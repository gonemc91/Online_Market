package com.example.appmarket.glue.catalog.mappers

import com.em.catalog.domain.entitys.filter.SortBy
import com.example.data.product.entities.SortByDataValue
import javax.inject.Inject

class SortByMapper @Inject constructor() {

    fun toSortByDataValue(sortBy: SortBy): SortByDataValue {
        return when (sortBy){
            SortBy.PRICE -> SortByDataValue.PRICE
            SortBy.NAME -> SortByDataValue.NAME
            SortBy.RATING -> SortByDataValue.RATING
        }
    }
}