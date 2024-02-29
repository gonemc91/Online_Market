package com.em.online_market.glue.catalog.mappers

import com.em.catalog.domain.entitys.filter.ProductFilter
import com.example.appmarket.glue.catalog.mappers.SortByMapper
import com.example.appmarket.glue.catalog.mappers.SortOrderMapper
import com.example.data.product.entities.ProductDataFilter
import javax.inject.Inject

class ProductFilterMapper @Inject constructor(
    private val sortOrderMapper: SortOrderMapper,
    private val sortByMapper: SortByMapper,
) {

    fun toProductDataFilter(filter: ProductFilter): ProductDataFilter {
        return ProductDataFilter(
            tag = filter.tag,
            sortBy = sortByMapper.toSortByDataValue(filter.sortBy),
            sortOrder = sortOrderMapper.toSortOrderDataValue(filter.sortOrder),
        )
    }
}