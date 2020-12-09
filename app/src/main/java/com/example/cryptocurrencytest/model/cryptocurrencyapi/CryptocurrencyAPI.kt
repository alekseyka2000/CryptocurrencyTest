package com.example.cryptocurrencytest.model.cryptocurrencyapi

import com.example.cryptocurrencytest.model.entity.CryptocurrencyList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrencyAPI {

    @GET("v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=b41f293e-1727-4822-8677-7ce3726d1f5e")
    fun getCryptocurrencyList (
       // @Query("CMC_PRO_API_KEY")apiKey: String
    ): Single<CryptocurrencyList>
}