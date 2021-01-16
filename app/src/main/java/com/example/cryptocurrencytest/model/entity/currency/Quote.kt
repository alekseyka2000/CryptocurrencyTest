package com.example.cryptocurrencytest.model.entity.currency

import com.google.gson.annotations.SerializedName

data class Quote (
	@SerializedName("USD") val uSD : USD
)