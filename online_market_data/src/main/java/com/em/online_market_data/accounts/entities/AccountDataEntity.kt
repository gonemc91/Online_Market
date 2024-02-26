package com.em.online_market_data.accounts.entities

data class AccountDataEntity (
    val id: Long,
    val username: String,
    val surname: String,
    val telephoneNumber: String,
    val createdAtMillis: Long
)
