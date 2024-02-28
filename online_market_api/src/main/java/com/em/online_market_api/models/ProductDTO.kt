package com.em.online_market_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




@Serializable
data class ProductDTO(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("price") val price: PriceDTO,
    @SerialName("feedback") val feedback: FeedbackDTO,
    @SerialName("tags") val tags: List<String>,
    @SerialName("available") val available: Int,
    @SerialName("description") val description: String,
    @SerialName("info") val info: List<InfoProductDTO>,
    @SerialName("ingredients") val ingredients: String,
    )
