package com.example.cryptocurrencytest.model.entity.currency

data class USD (
	val price : String,
	val volume_24h : String,
	val percent_change_1h : String,
	val percent_change_24h : String,
	val percent_change_7d : String,
	val market_cap : String,
	val last_updated : String
)