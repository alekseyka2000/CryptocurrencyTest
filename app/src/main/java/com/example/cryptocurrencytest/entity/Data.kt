package com.example.cryptocurrencytest.entity

data class Data (
	val id : Int,
	val name : String,
	val symbol : String,
	val slug : String,
	val num_market_pairs : Int,
	val date_added : String,
	val tags : List<String>,
	val max_supply : Int,
	val circulating_supply : Int,
	val total_supply : Int,
	val platform : String,
	val cmc_rank : Int,
	val last_updated : String,
	val quote : Quote
)