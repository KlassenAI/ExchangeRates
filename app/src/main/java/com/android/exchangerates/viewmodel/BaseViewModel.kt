package com.android.exchangerates.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.exchangerates.entities.Rate
import com.android.exchangerates.repository.RateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val repository = RateRepository()
    val dailyRates = MutableLiveData<List<Rate>>()
    val currentRate = MutableLiveData<Rate>()
    val currentRubles = MutableLiveData<String>()

    private var job: Job? = null

    fun getDailyRates() {
        job = CoroutineScope(IO).launch {
            val response = repository.getDailyRates()
            Log.e("getDailyRates", "Загрузка курсов валют")
            if (response.isSuccessful) {
                val rates = response.body()?.rates?.getAll()!!
                dailyRates.postValue(rates)
                currentRate.postValue(rates[0])
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}