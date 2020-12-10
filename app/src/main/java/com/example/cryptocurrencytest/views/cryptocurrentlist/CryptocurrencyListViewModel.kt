package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CryptocurrencyListViewModel : ViewModel(), KoinComponent {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData
    val cryptocurrencyService by inject<CryptocurrencyService>()

    @SuppressLint("CheckResult")
    fun fetchCryptocurencyData() {
        cryptocurrencyService.getCryptocurrency()
            .map { Mapper().invoke(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { mutableLiveData.value = it
                    Log.d("log", "${it}")},
                {Log.d("log", "${it.message}") }
            )
    }

    class Mapper: (CryptocurrencyList) -> List<PrepareCryptocurrencyData> {
        override fun invoke(p1: CryptocurrencyList): List<PrepareCryptocurrencyData> {
            val newList = mutableListOf<PrepareCryptocurrencyData>()
            p1.data.forEach{newList.add(PrepareCryptocurrencyData(it.symbol, it.name, it.quote.uSD.price+" $"))}
            return newList
        }
    }
}