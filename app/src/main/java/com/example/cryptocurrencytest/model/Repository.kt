package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.currency.Data
import io.reactivex.Single

class Repository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {

    fun fetchCryptocurencyData() = cryptocurrencyService.getCryptocurrency()
    //: Single<List<PrepareCryptocurrencyData>> =
//        cryptocurrencyService.getCryptocurrency()
//            .map {
//                db.cryptocurrencyDAO().delete()
//                dataMapping(it)
//            }

    fun fetchSymbolURL(currencyData: Data): Single<String> {
        return cryptocurrencyService.getCryptocurrencyMetadata(currencyData.symbol)
            .map { it.string().split("\",\"logo\":\"")[1].split("\",\"subreddit\":\"")[0] }
    }

//    private fun dataMapping(p1: CryptocurrencyList) = p1.data.map { data ->
//        var symbol = "Empty"
//        cryptocurrencyService.getCryptocurrencyMetadata(data.symbol)
//            .subscribe({ logo ->
//                Log.d("TAG", "+")
//                symbol = "logo.data.bTC.logo"
//            }, { e -> Log.e("TAG", e.message ?: "Error") })
//        db.cryptocurrencyDAO().insertData(
//            listOf(
//                CryptocurrencyDataDB(
//                    imageReference = symbol,
//                    cryptocurrencyName = data.name,
//                    cryptocurrencyPriceUSD = data.quote.uSD.price,
//                    symbol = data.symbol,
//                    slug = data.slug,
//                    numMarketPairs = data.num_market_pairs,
//                    dateAdded = data.date_added
//                )
//            )
//        )
//
//        PrepareCryptocurrencyData(symbol, data.name, data.quote.uSD.price + " $")
//    }
}