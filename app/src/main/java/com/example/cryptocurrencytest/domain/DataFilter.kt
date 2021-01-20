package com.example.cryptocurrencytest.domain

import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface DataFilter {

    fun getData(): Observable<List<PrepareCryptocurrencyData>>
    fun updateCryptocurrencyData(): Observable<Pair<Boolean, Disposable>>
}