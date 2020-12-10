package com.example.cryptocurrencytest.di

import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyServiceImpl
import org.koin.dsl.module


val cryptocurrencyServiceModule = module {
    single<CryptocurrencyService> { CryptocurrencyServiceImpl() }
    single { Repository() }
}