package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.entity.currency.Data
import io.reactivex.Observable
import io.reactivex.Single

class Repository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {

    fun fetchCryptocurencyData() = cryptocurrencyService.getCryptocurrency()

    fun fetchSymbolURL(currencyData: Data): Single<String> {
        return cryptocurrencyService.getCryptocurrencyMetadata(currencyData.symbol)
            .map { it.string().split("\",\"logo\":\"")[1].split("\",\"subreddit\":\"")[0] }
    }

    fun fetchDataFromDB(): Observable<List<PrepareCryptocurrencyData>> {
        return db.cryptocurrencyDAO().getDataList().map {
            val dataList = mutableListOf<PrepareCryptocurrencyData>()
            it.forEach { data ->
                dataList.add(
                    PrepareCryptocurrencyData(
                        imageReference = data.imageReference,
                        cryptocurrencyName = data.cryptocurrencyName,
                        cryptocurrencyPriceUSD = data.cryptocurrencyPriceUSD
                    )
                )
            }
            return@map dataList
        }
    }

    fun insertCurrencyData(currencyData: Data, symbol: String) {
        db.cryptocurrencyDAO().insertData(
            listOf(
                CryptocurrencyDataDB(
                    imageReference = symbol,
                    cryptocurrencyName = currencyData.name,
                    cryptocurrencyPriceUSD = currencyData.quote.uSD.price,
                    symbol = currencyData.symbol,
                    slug = currencyData.slug,
                    numMarketPairs = currencyData.num_market_pairs,
                    dateAdded = currencyData.date_added
                )
            )
        )
    }
}