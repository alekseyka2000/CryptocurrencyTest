package com.example.cryptocurrencytest.model.mapper

abstract class Mapper<From, To> {

    abstract fun map(from: From) : To

    fun map(items: List<From>): List<To> {
        return items.map { map(it) }
    }
}