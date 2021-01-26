package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

// ты вроде собирался интерфейс репозитория вынести в домейн?
interface DataRepository {

    fun updateCryptocurrencyData(): Observable<Pair<Boolean, Disposable>>

    fun getData(): Observable<List<CryptocurrencyDataDB>>
}