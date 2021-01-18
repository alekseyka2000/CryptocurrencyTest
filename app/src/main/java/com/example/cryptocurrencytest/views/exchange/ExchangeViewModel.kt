package com.example.cryptocurrencytest.views.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrencytest.model.CryptocurrencyDataRepository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.views.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ExchangeViewModel(private val cryptocurrencyDataRepository: CryptocurrencyDataRepository) : BaseViewModel() {

    private val cryptocurrencyMutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val cryptocurrencyLiveData: LiveData<List<PrepareCryptocurrencyData>> = cryptocurrencyMutableLiveData

    init {
        subscriptions.add(cryptocurrencyDataRepository.fetchCryptocurencyData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { },//mutableLiveData.value = it },
                { Log.d("TAG", "${it.message}") }
            )
        )
    }
}