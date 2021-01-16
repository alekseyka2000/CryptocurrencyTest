package com.example.cryptocurrencytest.model.cryptocurrencyapi

interface ApiFactory {
    fun getApiServiceForCurrencyList(): CryptocurrencyAPI
    fun getApiService(): CryptocurrencyAPI
}