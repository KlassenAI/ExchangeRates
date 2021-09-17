package com.android.exchangerates.repository

import com.android.exchangerates.service.RateService
import com.android.exchangerates.service.RetrofitInstance

class RateRepository {

    private var apiService: RateService = RetrofitInstance.getClient()

    suspend fun getDailyRates() = apiService.getDailyRates()
}