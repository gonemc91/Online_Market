package com.em.catalog.domain.entitys.filter

data class Tag(
    val uuidTag: Long,
    val name: String,
    val active: Boolean = false,
)
