package com.example.cryptocurrencytest.model.cryptocurrencyapi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// добавь OkHttpLoggingInterceptor, чтоыб можно было в логах смотреть запросы
class ApiFactoryImpl : ApiFactory {

    // почему два апи? нужен как минимум поясняющий комментарий, как максиумм - а точно ли нужно 2?
    override fun getApiServiceForCurrencyList(): CryptocurrencyAPI =
        Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptocurrencyAPI::class.java)

    override fun getApiService(): CryptocurrencyAPI =
        Retrofit.Builder()
            // дублирование урла
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CryptocurrencyAPI::class.java)
}