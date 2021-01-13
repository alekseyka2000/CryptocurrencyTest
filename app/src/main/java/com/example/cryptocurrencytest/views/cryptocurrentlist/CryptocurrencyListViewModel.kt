package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CryptocurrencyListViewModel(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData

    fun fetchCryptocurencyData() = repository.fetchCryptocurencyData()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                mutableLiveData.value = it
                Log.d("logi", it.toString())
            },
            { Log.d("logi", "${it.message}") }
        )
}