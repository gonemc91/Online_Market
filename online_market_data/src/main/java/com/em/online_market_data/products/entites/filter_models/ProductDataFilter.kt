package com.example.data.product.entities

import com.em.online_market_data.products.entites.filter_models.SortOrderDataValue

data class ProductDataFilter(
    val tag: String? = null,
    val sortBy: SortByDataValue = SortByDataValue.NAME,
    val sortOrder: SortOrderDataValue = SortOrderDataValue.ASC,
){
    companion object{
        val DEFAULT = ProductDataFilter()
    }
}

