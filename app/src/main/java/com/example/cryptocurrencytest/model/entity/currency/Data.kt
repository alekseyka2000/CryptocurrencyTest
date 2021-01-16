package com.example.cryptocurrencytest.model.entity.currency

// в Джаве/котлине всё же лучше использовать CamelCase, чтобы глаз не дёргался
// если атк требует  апи -  клади в @SerializedName
data class Data (
	val id : Int,
	val name : String,
	val symbol : String,
	val slug : String,
	val num_market_pairs : Int,
	val date_added : String,
	val tags : List<String>,
	val max_supply : Double,
	val circulating_supply : Double,
	val total_supply : Double,
	val platform : Any,
	val cmc_rank : Int,
	val last_updated : String,
	val quote : Quote
)