package com.android.exchangerates.callback

import com.android.exchangerates.entities.Rate

interface SelectRateCallback {

    fun setCurrentRate(rate: Rate)
}