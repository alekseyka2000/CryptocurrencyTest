package com.example.cryptocurrencytest.model.cryptocurrencyapi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactoryImpl : ApiFactory {
    override fun getApiServiceForCurrencyList(): CryptocurrencyAPI =
        Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptocurrencyAPI::class.java)

    override fun getApiService(): CryptocurrencyAPI =
        Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CryptocurrencyAPI::class.java)
}