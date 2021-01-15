package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.metadata.MetadataCurrency
import io.reactivex.Single

class CryptocurrencyServiceImpl(
    private val apiFactory: ApiFactory = ApiFactoryImpl()
) : CryptocurrencyService {

    override fun getCryptocurrency(): Single<CryptocurrencyList> =
        apiFactory.getApiService().getCryptocurrencyPriceList()

    override fun getCryptocurrencyMetadata(symbol: String): Single<MetadataCurrency> =
        apiFactory.getApiService().getCryptocurrencyMetaDataList(symbol)
}