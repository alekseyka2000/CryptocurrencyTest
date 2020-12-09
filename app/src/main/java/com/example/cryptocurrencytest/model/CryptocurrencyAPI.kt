package com.example.cryptocurrencytest.model

import io.reactivex.Single
import retrofit2.http.GET

interface CryptocurrencyAPI {

    @GET("Europe/Minsk")
    fun getCurrentDateInMinsk (): Single<String>
}