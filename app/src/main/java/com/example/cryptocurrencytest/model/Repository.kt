package com.example.cryptocurrencytest.model

import android.util.Log
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.Data
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class Repository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {


    fun fetchCryptocurencyData(): Single<List<PrepareCryptocurrencyData>> =
        cryptocurrencyService.getCryptocurrency()
            .map {
                db.cryptocurrencyDAO().delete()
                dataMapping(it)
            }

    fun fetchSymbolURL() =
        cryptocurrencyService.getCryptocurrencyMetadata("BTC")

    private fun dataMapping(p1: CryptocurrencyList): List<PrepareCryptocurrencyData> {

//        Single.create<List<Data>> { emitter -> emitter.onSuccess(p1.data) }
//            .delay
        return p1.data.map { data ->
            var symbol = "Empty"
            cryptocurrencyService.getCryptocurrencyMetadata(data.symbol)
                .subscribe({
                    Log.d("TAG", "+")
                    symbol = "it.data.bTC.logo" ?: "Null"
                }, { e -> Log.e("TAG", e.message ?: "Error") })
            db.cryptocurrencyDAO().insertData(
                listOf(
                    CryptocurrencyDataDB(
                        imageReference = symbol,
                        cryptocurrencyName = data.name,
                        cryptocurrencyPriceUSD = data.quote.uSD.price,
                        symbol = data.symbol,
                        slug = data.slug,
                        numMarketPairs = data.num_market_pairs,
                        dateAdded = data.date_added
                    )
                )
            )

            PrepareCryptocurrencyData(symbol, data.name, data.quote.uSD.price + " $")
        }
        //return emptyList()
    }
}