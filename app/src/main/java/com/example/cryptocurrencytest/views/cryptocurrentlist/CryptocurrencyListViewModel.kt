package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.entity.currency.Data
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CryptocurrencyListViewModel(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData
    private lateinit var subscriptions: CompositeDisposable

    fun fetchCryptocurencyData() {
        subscriptions = CompositeDisposable()
        getCryptocurencyList()
    }

    private fun getCryptocurencyList() {
        subscriptions.add(
            repository.fetchCryptocurencyData()
                .map {
                    it.data
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { request(it) },
                    // { mutableLiveData.value = it },
                    { Log.d("logi", "${it.message}") }
                )
        )
    }

    fun request(list: List<Data>) {
        subscriptions.add(Observable.zip(
            Observable.interval(2, TimeUnit.SECONDS),
            Observable.fromIterable(list)
        ) { _, list -> list }
            .subscribe { currencyData ->
                getCryptocurrencyImageURL(currencyData)
            }
        )
    }

    fun getCryptocurrencyImageURL(currencyData: Data) {
        subscriptions.add(repository.fetchSymbolURL(currencyData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("TAG", it) },
                { Log.d("TAG", "${it.message}") }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}