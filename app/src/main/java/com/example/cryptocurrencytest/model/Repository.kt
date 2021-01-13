package com.example.cryptocurrencytest.model

import android.util.Log
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class Repository(private val cryptocurrencyService: CryptocurrencyService) {

    fun fetchCryptocurencyData(): Single<List<PrepareCryptocurrencyData>> =
        cryptocurrencyService.getCryptocurrency()
            .map {
                Mapper().invoke(it) }
            .subscribeOn(Schedulers.io())

    class Mapper : (CryptocurrencyList) -> List<PrepareCryptocurrencyData> {
        override fun invoke(p1: CryptocurrencyList): List<PrepareCryptocurrencyData> {
            val newList = mutableListOf<PrepareCryptocurrencyData>()
            Log.d("logi", "it.toString()")
            p1.data.forEach {
                newList.add(
                    PrepareCryptocurrencyData(
                        it.symbol,
                        it.name,
                        it.quote.uSD.price + " $"
                    )
                )
            }
            return newList
        }
    }
}