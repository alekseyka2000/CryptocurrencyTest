package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import io.reactivex.Single

class CryptocurrencyServiceImpl(
    private val apiFactory: ApiFactory = ApiFactoryImpl()
) : CryptocurrencyService {

    override fun getCryptocurrency(): Single<CryptocurrencyList> =
        apiFactory.getApiService().getCryptocurrencyPriceList()

}