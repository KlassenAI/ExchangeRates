package com.android.exchangerates.entities

import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("Date")
    var date: String,
    @SerializedName("PreviousDate")
    var previousDate: String,
    @SerializedName("PreviousURL")
    var previousURL: String,
    @SerializedName("Timestamp")
    var timestamp: String,
    @SerializedName("Valute")
    var rates: Rates
)