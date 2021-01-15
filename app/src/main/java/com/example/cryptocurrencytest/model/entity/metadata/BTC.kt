package com.example.cryptocurrencytest.model.entity.metadata

import com.google.gson.annotations.SerializedName

data class BTC (

	val id : Int,
	val name : String,
	val symbol : String,
	val category : String,
	val description : String,
	val slug : String,
	val logo : String = "Empty",
	val subreddit : String,
	val notice : String,
	val tags : List<String>,
	@SerializedName("tag-names") val tagNames : List<String>,
	@SerializedName("tag-groups") val tagGroup : List<String>,
	val urls : Urls,
	val platform : String,
	val date_added : String,
	val twitter_username : String,
	val is_hidden : Int
)