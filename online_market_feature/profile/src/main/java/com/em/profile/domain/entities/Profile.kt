package com.em.profile.domain.entities

data class Profile(
    val id: Long,
    val username: String,
    val surname: String,
    val telephoneNumber: String,
    val createdAtMillis: Long
)
