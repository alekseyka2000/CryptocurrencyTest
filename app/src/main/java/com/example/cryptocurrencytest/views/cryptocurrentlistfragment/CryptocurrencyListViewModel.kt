package com.example.cryptocurrencytest.views.cryptocurrentlistfragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.entity.Data
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class CryptocurrencyListViewModel : ViewModel(), KoinComponent {

    private val mutableLiveData = MutableLiveData<List<Data>>()
    val liveData: LiveData<List<Data>> = mutableLiveData
    val cryptocurrencyService by inject<CryptocurrencyService>()

    @SuppressLint("CheckResult")
    fun fetchCryptocurencyData() {
        cryptocurrencyService.getCryptocurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { mutableLiveData.value = it.data },
                {Log.d("log", "${it.message}") }
            )
    }
}