package com.example.cryptocurrencytest.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrency")
data class CryptocurrencyDataDB(
    @ColumnInfo(name = "image_url") val imageReference: String?,
    @PrimaryKey @ColumnInfo(name = "ID") val cryptocurrencyName: String,
    @ColumnInfo(name = "price") val cryptocurrencyPriceUSD: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "num_market_pairs") val numMarketPairs: Int,
    @ColumnInfo(name = "date_added") val dateAdded: String
)