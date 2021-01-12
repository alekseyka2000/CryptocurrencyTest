package com.example.cryptocurrencytest.views.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.android.schedulers.AndroidSchedulers

class ExchangeViewModel(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData

    fun fetchCryptocurencyData() = repository.fetchCryptocurencyData()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                mutableLiveData.value = it
                Log.d("log", it.toString())
            },
            { Log.d("log", "${it.message}") }
        )
}