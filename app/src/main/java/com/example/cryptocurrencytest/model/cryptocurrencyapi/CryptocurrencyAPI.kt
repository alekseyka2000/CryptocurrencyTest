package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.currency.CryptocurrencyList
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrencyAPI {

    // хардкодить ключ в каждом урле - такое себе. А тем более дублировать.
    // вынеси в константы и добавляй в запросы как query
    // а ещё лучше через интерсептор https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request#:~:text=Adding%20query%20parameters%20to%20single,the%20fields%20to%20the%20url.
    @GET("v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=b41f293e-1727-4822-8677-7ce3726d1f5e")
    fun getCryptocurrencyPriceList (
    ): Observable<CryptocurrencyList>

    @GET("v1/cryptocurrency/info?CMC_PRO_API_KEY=b41f293e-1727-4822-8677-7ce3726d1f5e")
    fun getCryptocurrencyMetaDataList (
       @Query("symbol") symbol: String
    ): Single<ResponseBody>
}