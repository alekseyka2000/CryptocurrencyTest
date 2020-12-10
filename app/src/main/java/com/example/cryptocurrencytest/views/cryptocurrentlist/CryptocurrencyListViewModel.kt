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
class CryptocurrencyListViewModel : ViewModel(), KoinComponent {

    private val mutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val liveData: LiveData<List<PrepareCryptocurrencyData>> = mutableLiveData
    private val repository by inject<Repository>()

    @SuppressLint("CheckResult")
    fun fetchCryptocurencyData() {
        repository.fetchCryptocurencyData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mutableLiveData.value = it
                    Log.d("log", it.toString())
                },
                { Log.d("log", "${it.message}") }
            )
    }
}