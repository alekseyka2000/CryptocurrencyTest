package com.example.cryptocurrencytest.model.entity

import com.google.gson.annotations.SerializedName

data class Quote (
	@SerializedName("USD") val uSD : USD
)