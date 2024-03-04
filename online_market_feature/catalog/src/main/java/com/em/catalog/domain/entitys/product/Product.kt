package com.em.catalog.domain.entitys.product

data class Product(
    val uuid: Long,
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoProduct>,
    val ingredients: String,
    val images: ProductImages?
)