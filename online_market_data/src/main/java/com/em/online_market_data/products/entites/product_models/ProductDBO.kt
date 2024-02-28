package com.em.online_market_data.products.entites.product_models

data class ProductDBO(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceDBO,
    val feedback: FeedbackDBO,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoProductDBO>,
    val ingredients: String,
    val images: ProductImagesDBO?
)
