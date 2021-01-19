package com.example.cryptocurrencytest.model.mapper

import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.currency.Data

class MapperCurrencyDataToCryptocurrencyDataDB: Mapper<Pair<Data, String>, CryptocurrencyDataDB>() {
    override fun map(from: Pair<Data, String>): CryptocurrencyDataDB {
        return CryptocurrencyDataDB(
            imageReference = from.second,
            cryptocurrencyName = from.first.name,
            cryptocurrencyPriceUSD = from.first.quote.uSD.price,
            symbol = from.first.symbol,
            slug = from.first.slug,
            numMarketPairs = from.first.num_market_pairs,
            dateAdded = from.first.date_added
        )
    }
}