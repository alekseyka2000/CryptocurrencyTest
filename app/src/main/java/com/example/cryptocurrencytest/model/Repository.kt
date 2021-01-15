package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.Single

class Repository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {


    fun fetchCryptocurencyData(): Single<List<PrepareCryptocurrencyData>> =
        cryptocurrencyService.getCryptocurrency()
            .map { dataMapping(it) }

    private fun dataMapping(p1: CryptocurrencyList) =
        p1.data.map {
            PrepareCryptocurrencyData(it.symbol, it.name, it.quote.uSD.price + " $")
        }
}