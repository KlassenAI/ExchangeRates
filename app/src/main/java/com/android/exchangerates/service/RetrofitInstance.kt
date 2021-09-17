package com.android.exchangerates.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var instance: RateService? = null

    private const val BASE_URL = "https://www.cbr-xml-daily.ru/"

    fun getClient(): RateService {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RateService::class.java)
        }
        return instance!!
    }
}