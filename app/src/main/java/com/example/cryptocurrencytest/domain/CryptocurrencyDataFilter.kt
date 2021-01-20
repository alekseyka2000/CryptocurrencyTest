package com.example.cryptocurrencytest.domain

import com.example.cryptocurrencytest.model.DataRepository
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.mapper.MapperCryptocurrencyDataDBToPrepareCryptocurrencyData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class CryptocurrencyDataFilter(private val repository: DataRepository): DataFilter {
    override fun getData(): Observable<List<PrepareCryptocurrencyData>> {
        return repository.getData().map {
            MapperCryptocurrencyDataDBToPrepareCryptocurrencyData()
                .map(it.sortedByDescending { by -> by.numMarketPairs })
        }
    }

    override fun updateCryptocurrencyData(): Observable<Pair<Boolean, Disposable>> {
        return repository.updateCryptocurrencyData()
    }
}