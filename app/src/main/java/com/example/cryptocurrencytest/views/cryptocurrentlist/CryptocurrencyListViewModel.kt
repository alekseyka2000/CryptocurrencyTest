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

    // переименуй
    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData
    // вынеси в базовую вьюмодель и всегда наследуйся от неё
    private lateinit var subscriptions: CompositeDisposable

    fun fetchCryptocurencyData() {
        // а если этот метод не вызовется то что, всё, переменная останется не инициализирована?
        // ты, возможно, мне возразишь, что он обязательно вызовется, ведь у фрагмента обязательно убдет вызван onViewCraeted()
        // Если так,то ты сильно связываешь вьюмодель и конкретную реализацию вью - CryptocurrencyListFragment
        // а надо, чтобы вьюмодель была абсолютно независима от вью
        subscriptions = CompositeDisposable()
        // а почеум ты одновременно делаешь запросы в сеть и лезешь в БД?

        getCryptocurencyList()
        fetchDataFromDB()
    }

    private fun getCryptocurencyList() {
        subscriptions.add(
            repository.fetchCryptocurencyData()
                    // маппинг в классе или хоть отдельном методе
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
                    // слой вью/домейн не должен знать, как откуда ты поулчаешь картинки,
                // это всё должно быть закрыто репозиторием, слой вью поулчает готовые данные
                    //и не должен беспокоиться где их брать - в БД, из сети и т д
                    // и уж точно вью/домейн не знает, что такое реквест и не управляет ими
                .subscribe(
                    { sendRequestIn1Minute(it) },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    private fun sendRequestIn1Minute(listRequests: List<List<Data>>) {
        getCryptocurrencyImageURL(listRequests[0]) // в котлине есть .first()
        //  нечитабельно, исправь
        subscriptions.add(Observable.zip(
            Observable.interval(60, TimeUnit.SECONDS),
            Observable.fromIterable(listRequests.takeLast(listRequests.size - 1))
        ) { _, currencyDataList -> currencyDataList }// тут красным подчеркивает - ??? грязь в готовом коде быть не должно
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