package com.example.cryptocurrencytest.model.entity

data class USD (
	val price : Double,
	val volume_24h : Double,
	val percent_change_1h : Double,
	val percent_change_24h : Double,
	val percent_change_7d : Double,
	val market_cap : Int,
	val last_updated : String
)