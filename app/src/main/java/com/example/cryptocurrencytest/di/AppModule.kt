package com.example.cryptocurrencytest.di

import com.example.cryptocurrencytest.model.Repository
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyServiceImpl
import com.example.cryptocurrencytest.views.cryptocurrentlist.CryptocurrencyListViewModel
import com.example.cryptocurrencytest.views.exchange.ExchangeFragment
import com.example.cryptocurrencytest.views.exchange.ExchangeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cryptocurrencyServiceModule = module {
    single<CryptocurrencyService> { CryptocurrencyServiceImpl() }
    single { Repository(cryptocurrencyService = get()) }
}
val viewModelModule = module {
    viewModel { CryptocurrencyListViewModel(repository= get()) }
    viewModel { ExchangeViewModel(repository = get()) }
}