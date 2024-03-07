package com.em.catalog.domain.entitys.filter

data class ProductFilter(
    val tag: String? = null,
    val sortBy: SortBy = SortBy.RATING,
    val sortOrder: SortOrder = SortOrder.ASC,
){
    companion object{
        val EMPTY = ProductFilter()
    }
}
