package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import io.reactivex.Single

interface CryptocurrencyService {
    fun getCryptocurrency(): Single<CryptocurrencyList>
}