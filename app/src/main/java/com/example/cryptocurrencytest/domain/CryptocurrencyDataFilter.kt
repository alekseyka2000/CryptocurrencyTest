package com.example.cryptocurrencytest.domain

import com.example.cryptocurrencytest.model.CryptocurrencyDataRepository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.mapper.MapperCryptocurrencyDataDBToPrepareCryptocurrencyData
import io.reactivex.Observable

class CryptocurrencyDataFilter(private val repository: CryptocurrencyDataRepository) {
    fun getData(): Observable<List<PrepareCryptocurrencyData>> {
        return repository.getDataFromDB().map {
            MapperCryptocurrencyDataDBToPrepareCryptocurrencyData()
                .map(it.sortedByDescending { by -> by.numMarketPairs })
        }
    }
}