package com.example.cryptocurrencytest.di

import com.example.cryptocurrencytest.domain.CryptocurrencyDataFilter
import com.example.cryptocurrencytest.domain.DataFilter
import com.example.cryptocurrencytest.model.CryptocurrencyDataRepository
import com.example.cryptocurrencytest.model.DataRepository
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyServiceImpl
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.views.cryptocurrentlist.CryptocurrencyListViewModel
import com.example.cryptocurrencytest.views.exchange.ExchangeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cryptocurrencyServiceModule = module {
    single<CryptocurrencyService> { CryptocurrencyServiceImpl() }
    single { CryptocurrencyDB.getDB(get()) }
    single<DataRepository> { CryptocurrencyDataRepository(cryptocurrencyService = get(), db = get()) }
    single<DataFilter> { CryptocurrencyDataFilter(repository = get()) }
}
val viewModelModule = module {
    viewModel {
        CryptocurrencyListViewModel(
            cryptocurrencyDataFilter = get()
        )
    }
    viewModel { ExchangeViewModel(cryptocurrencyDataRepository = get()) }
}