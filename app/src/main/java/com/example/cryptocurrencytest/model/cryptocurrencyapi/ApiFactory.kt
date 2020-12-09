package com.example.cryptocurrencytest.model.cryptocurrencyapi

interface ApiFactory {
    fun getApiService(): CryptocurrencyAPI
}