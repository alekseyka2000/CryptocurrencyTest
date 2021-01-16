package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.currency.CryptocurrencyList
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody

interface CryptocurrencyService {
    fun getCryptocurrency(): Observable<CryptocurrencyList>
    fun getCryptocurrencyMetadata(symbol: String): Single<ResponseBody>
}