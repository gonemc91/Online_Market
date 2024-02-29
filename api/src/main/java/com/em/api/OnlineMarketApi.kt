package com.em.api

import com.em.api.models.ResponseDTO
import retrofit2.http.GET

/**
 * [Mock URL:(https://run.mocky.io/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010)]
 */


interface OnlineMarketApi {
    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProductsList(): ResponseDTO
}



