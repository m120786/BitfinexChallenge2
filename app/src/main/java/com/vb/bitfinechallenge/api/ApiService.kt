package com.vb.bitfinechallenge.api

import com.vb.bitfinechallenge.model.network.TickerDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v1/pubticker/{pairId}")
    suspend fun getPairById(@Path("pairId") pairId: String): TickerDto

}