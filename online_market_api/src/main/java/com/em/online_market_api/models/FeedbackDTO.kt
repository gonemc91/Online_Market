package com.em.online_market_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class FeedbackDTO (
    @SerialName("count")val count: Int,
    @SerialName("rating")val rating: Double,
)
