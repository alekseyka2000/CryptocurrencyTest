package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrencytest.model.CryptocurrencyDataRepository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.entity.currency.Data
import com.example.cryptocurrencytest.model.mapper.MapperCryptocurrencyListToListOfCryptocurrencyListBy25Elements
import com.example.cryptocurrencytest.views.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CryptocurrencyListViewModel(private val cryptocurrencyDataRepository: CryptocurrencyDataRepository) : BaseViewModel() {

    private val cryptocurrencyMutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val cryptocurrencyLiveData: LiveData<List<PrepareCryptocurrencyData>> = cryptocurrencyMutableLiveData

    init {
        Log.d("Creation order", "VM")
        getCryptocurencyList()
        fetchDataFromDB()
    }

    private fun getCryptocurencyList() {
        subscriptions.add(
            cryptocurrencyDataRepository.fetchCryptocurencyData()
                .map {
                    val list = MapperCryptocurrencyListToListOfCryptocurrencyListBy25Elements().map(it)
                    list
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // слой вью/домейн не должен знать, как откуда ты поулчаешь картинки,
                // это всё должно быть закрыто репозиторием, слой вью поулчает готовые данные
                //и не должен беспокоиться где их брать - в БД, из сети и т д
                // и уж точно вью/домейн не знает, что такое реквест и не управляет ими
                .subscribe(
                    {
                        sendRequestIn1Minute(it) },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    private fun sendRequestIn1Minute(listRequests: List<List<Data>>) {
        getCryptocurrencyImageURL(listRequests.first())
        //  нечитабельно, исправь
        val timer = Observable.interval(60, TimeUnit.SECONDS)
        val cryptocurrencyList = Observable.fromIterable(listRequests.takeLast(listRequests.size - 1))
        subscriptions.add(Observable.zip(timer, cryptocurrencyList) { _, currencyDataList -> currencyDataList }// тут красным подчеркивает - ??? грязь в готовом коде быть не должно
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
                cryptocurrencyDataRepository.fetchSymbolURL(currencyData)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { cryptocurrencyDataRepository.insertCurrencyData(currencyData, it) },
                        { Log.d("TAG", "${it.message}") }
                    )
            )
        }
    }

    private fun fetchDataFromDB() {
        subscriptions.add(
            cryptocurrencyDataRepository.fetchDataFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cryptocurrencyMutableLiveData.value = it },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    override fun onCleared() {
        Log.d("Creation order", "VM2")
        super.onCleared()
        subscriptions.clear()
    }
}