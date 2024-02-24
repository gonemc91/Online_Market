package com.em.online_market_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PriceDTO (
    @SerialName("price") val price: String,
    @SerialName("discount") val discount: Int,
    @SerialName("priceWithDiscount") val priceWithDiscount: String,
    @SerialName("unit") val unit: String,
)