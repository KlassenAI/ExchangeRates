package com.android.exchangerates.service

import com.android.exchangerates.entities.RateResponse
import retrofit2.Response
import retrofit2.http.GET

interface RateService {

    @GET("daily_json.js")
    suspend fun getDailyRates(): Response<RateResponse>
}