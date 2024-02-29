package com.em.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResponseDTO(
    @SerialName("items") val items: List<ProductDTO>,
)