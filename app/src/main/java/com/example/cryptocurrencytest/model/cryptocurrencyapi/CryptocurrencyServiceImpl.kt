package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.currency.CryptocurrencyList
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody

class CryptocurrencyServiceImpl(
    private val apiFactory: ApiFactory = ApiFactoryImpl()
) : CryptocurrencyService {

    override fun getCryptocurrency(): Observable<CryptocurrencyList> =
        apiFactory.getApiServiceForCurrencyList().getCryptocurrencyPriceList()

    override fun getCryptocurrencyMetadata(symbol: String): Single<ResponseBody> =
        apiFactory.getApiService().getCryptocurrencyMetaDataList(symbol)
}