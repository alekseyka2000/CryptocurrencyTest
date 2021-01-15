package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.metadata.MetadataCurrency
import io.reactivex.Single

interface CryptocurrencyService {
    fun getCryptocurrency(): Single<CryptocurrencyList>
    fun getCryptocurrencyMetadata(symbol: String): Single<MetadataCurrency>
}