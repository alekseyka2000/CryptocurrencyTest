package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CryptocurrencyListViewModel(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData
    private lateinit var subscriptions: CompositeDisposable

    fun fetchCryptocurencyData() {
        subscriptions = CompositeDisposable()
        repository.fetchSymbolURL()
        subscriptions.add(repository.fetchCryptocurencyData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { mutableLiveData.value = it },
                { Log.d("logi", "${it.message}") }
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}