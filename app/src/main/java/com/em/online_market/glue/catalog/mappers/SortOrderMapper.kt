package com.example.appmarket.glue.catalog.mappers

import com.em.catalog.domain.entitys.filter.SortOrder
import com.em.online_market_data.products.entites.filter_models.SortOrderDataValue
import javax.inject.Inject

class SortOrderMapper @Inject constructor(){

    fun toSortOrderDataValue(sortOrder: SortOrder): SortOrderDataValue {
        return when (sortOrder){
            SortOrder.DESC -> SortOrderDataValue.DESC
            SortOrder.ASC -> SortOrderDataValue.ASC
        }
     }
}