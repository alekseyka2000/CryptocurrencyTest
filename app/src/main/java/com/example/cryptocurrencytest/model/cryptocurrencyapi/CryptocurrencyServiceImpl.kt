package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import io.reactivex.Single
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CryptocurrencyServiceImpl(
    private val apiFactory: ApiFactory = ApiFactoryImpl()
) : CryptocurrencyService {

    override fun getCryptocurrency(): Single<CryptocurrencyList> =
        apiFactory.getApiService().getCryptocurrencyList()

    companion object{
        val APIKEY = "b41f293e-1727-4822-8677-7ce3726d1f5e"
    }
}