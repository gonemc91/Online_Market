package com.em.catalog.domain.entitys.filter

data class ProductFilter(
    val sortBy: SortBy = SortBy.NAME,
){
    companion object{
        val EMPTY = ProductFilter()
    }
}
