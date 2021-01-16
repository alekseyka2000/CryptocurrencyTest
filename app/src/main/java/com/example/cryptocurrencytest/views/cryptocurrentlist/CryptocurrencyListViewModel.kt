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
        fetchDataFromDB()
    }

    private fun getCryptocurencyList() {
        subscriptions.add(
            repository.fetchCryptocurencyData()
                .map {
                    var list = it.data
                    val listOfList = mutableListOf<List<Data>>()
                    while (list.isNotEmpty()) {
                        listOfList.add(list.take(25))
                        list = list.takeLast(list.size - 25)
                    }
                    listOfList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { sendRequestIn1Minute(it) },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    private fun sendRequestIn1Minute(listRequests: List<List<Data>>) {
        getCryptocurrencyImageURL(listRequests[0])
        subscriptions.add(Observable.zip(
            Observable.interval(60, TimeUnit.SECONDS),
            Observable.fromIterable(listRequests.takeLast(listRequests.size - 1))
        ) { _, currencyDataList -> currencyDataList }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { currencyData ->
                getCryptocurrencyImageURL(currencyData)
            }
        )
    }

    private fun getCryptocurrencyImageURL(currencyDataList: List<Data>) {
        currencyDataList.forEach { currencyData ->
            subscriptions.add(
                repository.fetchSymbolURL(currencyData)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { repository.insertCurrencyData(currencyData, it) },
                        { Log.d("TAG", "${it.message}") }
                    )
            )
        }
    }

    private fun fetchDataFromDB() {
        subscriptions.add(
            repository.fetchDataFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mutableLiveData.value = it },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}