package com.example.cryptocurrencytest.views.cryptocurrentlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrencytest.domain.DataFilter
import com.example.cryptocurrencytest.model.CryptocurrencyDataRepository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.views.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CryptocurrencyListViewModel(
    private val cryptocurrencyDataFilter: DataFilter
) : BaseViewModel() {

    private val cryptocurrencyMutableLiveData = MutableLiveData<List<PrepareCryptocurrencyData>>()
    val cryptocurrencyLiveData: LiveData<List<PrepareCryptocurrencyData>> =
        cryptocurrencyMutableLiveData

    init {
        updateCryptocurrencyList()
        getData()
    }

    // хорошо что всё унёс в репозиторий, найс

    // чуть хуже, что у тебя вьюмодель инициализирует обновление данных, но возможно
    // плохо то,что ей это по сути не очень-то и надо, данных она с этого не получает
    // можно сделать так, чтобы запрос в БД запускал их обновление - на уровне логики или данных
    // или пусть тут хотя бы будет подписка на Completable, данные-то тут не нужны
    //
    private fun updateCryptocurrencyList() {
        subscriptions.add(
            cryptocurrencyDataFilter.updateCryptocurrencyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("TAG", "Update is successful: ${it.first}")
                        subscriptions.add(it.second)
                    },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    private fun getData() {
        subscriptions.add(
            cryptocurrencyDataFilter.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { cryptocurrencyMutableLiveData.value = it },
                    { Log.d("TAG", "${it.message}") }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}