package com.em.online_market_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class InfoProductDTO(
    @SerialName("title")val title: String,
    @SerialName("value")val value: String,
)
