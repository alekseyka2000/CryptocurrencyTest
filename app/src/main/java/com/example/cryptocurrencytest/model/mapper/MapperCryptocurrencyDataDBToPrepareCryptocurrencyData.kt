package com.example.cryptocurrencytest.model.mapper

import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData

class MapperCryptocurrencyDataDBToPrepareCryptocurrencyData:
    Mapper<CryptocurrencyDataDB, PrepareCryptocurrencyData>() {

    override fun map(from: CryptocurrencyDataDB): PrepareCryptocurrencyData {
        return  PrepareCryptocurrencyData(
            imageReference = from.imageReference,
            cryptocurrencyName = from.cryptocurrencyName,
            cryptocurrencyPriceUSD = from.cryptocurrencyPriceUSD
        )
    }
}