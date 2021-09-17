package com.android.exchangerates.entities

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

data class Rate(
    @SerializedName("CharCode")
    var charCode: String,
    @SerializedName("ID")
    var iD: String,
    @SerializedName("Name")
    var name: String,
    @SerializedName("Nominal")
    var nominal: Int,
    @SerializedName("NumCode")
    var numCode: String,
    @SerializedName("Previous")
    var previous: Double,
    @SerializedName("Value")
    var value: Double
) {
    fun getValueInRubles(rubles: Double): String {
        val d = rubles * nominal / value
        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.HALF_EVEN
        return df.format(d).replace(",", ".")
    }
}