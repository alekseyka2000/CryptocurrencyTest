package com.example.cryptocurrencytest.model

import android.util.Log
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.currency.Data
import com.example.cryptocurrencytest.model.mapper.MapperCryptocurrencyListToListOfCryptocurrencyListBy25Elements
import com.example.cryptocurrencytest.model.mapper.MapperCurrencyDataToCryptocurrencyDataDB
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class CryptocurrencyDataRepository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
): DataRepository {

    override fun updateCryptocurrencyData(): Observable<Pair<Boolean, Disposable>> {
        return cryptocurrencyService.makeGetCryptocurrencyDataRequest()
            .map {
                val list = MapperCryptocurrencyListToListOfCryptocurrencyListBy25Elements().map(it)
                prepareRequestsPackages(list)
            }
    }

    //separate list on a parts by 25, because if will send more then 25 requests in 1 minute to server get error 429
    private fun prepareRequestsPackages(listRequests: List<List<Data>>): Pair<Boolean, Disposable> {
        //get data about first 25 currencies
        var updateIsSucces = getCryptocurrencyImageURL(listRequests.first())
        val timer = Observable.interval(60, TimeUnit.SECONDS)
        val cryptocurrencyList =
            Observable.fromIterable(listRequests.takeLast(listRequests.size - 1))
        val disposable =
            Observable.zip(timer, cryptocurrencyList) { _, currencyDataList -> currencyDataList }
                .subscribe({ currencyData ->
                    updateIsSucces = updateIsSucces.and(getCryptocurrencyImageURL(currencyData))
                },
                    {})
        return Pair(updateIsSucces, disposable)
    }

    private fun getCryptocurrencyImageURL(currencyDataList: List<Data>): Boolean {
        var insertRowsCounter = 0
        currencyDataList.forEach { currencyData ->
            getSymbolURL(currencyData).map { insertCurrencyData(currencyData, it) }
                .subscribe({ insertRowsCounter++ },
                    {
                        Log.d(
                            "TAG",
                            "Insert data in the database about ${currencyData.name} failed"
                        )
                    })
        }
        return insertRowsCounter == currencyDataList.size
    }

    private fun getSymbolURL(currencyData: Data): Single<String> {
        return cryptocurrencyService.getCryptocurrencyMetadata(currencyData.symbol)
            //problem with response serialization, cannot get data as data class
            //so get result as string and fetch url from it
            .map {
                it.string().split("\",\"logo\":\"").last()
                    .split("\",\"subreddit\":\"").first()
            }
    }

    override fun getData() = db.cryptocurrencyDAO().getDataList()

    private fun insertCurrencyData(currencyData: Data, symbol: String): Completable {
        return db.cryptocurrencyDAO().insertData(
            MapperCurrencyDataToCryptocurrencyDataDB().map(Pair(currencyData, symbol))
        )
    }
}