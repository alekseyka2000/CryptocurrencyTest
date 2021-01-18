package com.example.cryptocurrencytest.model.mapper

import com.example.cryptocurrencytest.model.entity.currency.CryptocurrencyList
import com.example.cryptocurrencytest.model.entity.currency.Data

class MapperCryptocurrencyListToListOfCryptocurrencyListBy25Elements:
    Mapper<CryptocurrencyList, List<List<Data>>>() {

    override fun map(from: CryptocurrencyList): List<List<Data>> {
        var list = from.data
        val to = mutableListOf<List<Data>>()
        while (list.isNotEmpty()) {
            to.add(list.take(20))
            list = list.takeLast(list.size - 20)
        }
        return to
    }
}