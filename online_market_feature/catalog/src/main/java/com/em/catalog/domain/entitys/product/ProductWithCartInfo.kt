package com.em.catalog.domain.entitys.product

data class ProductWithCartInfo (
    val product: Product,
    val favourite: Boolean,
)