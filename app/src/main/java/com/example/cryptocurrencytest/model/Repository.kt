package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.entity.currency.Data
import io.reactivex.Observable
import io.reactivex.Single
/*
* я пишу комментарии тебе кириллицей на русском чтобы ты точно меня понял.
После исправления они все должны быть удалены.
В целом, кириллица в коде не ок, все поясняющие комментарии и документация должны быть на английском.
*/

// название класса ни о чём не говорит
class Repository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {

    // fetch - не ошибка, но обычно в названиях методов используется get
    fun fetchCryptocurencyData() = cryptocurrencyService.getCryptocurrency()

    fun fetchSymbolURL(currencyData: Data): Single<String> {
        return cryptocurrencyService.getCryptocurrencyMetadata(currencyData.symbol)
            //что это за маппинг? непонятно.
            // Добавь комментарий и/или вынеси в отдельный метод с говорящим названием,
            // может даже в отдельный класс.
            //
            // У меня есть подозрение что тут какие-то проблемы с десериализацией -
            // вероятно, нужно решить проблему более централизованно, где-то в апи билдере,
            // например, написать кастомный gson-deserializer для какого-то типа.
            .map { it.string().split("\",\"logo\":\"")[1].split("\",\"subreddit\":\"")[0] }
    }

    fun fetchDataFromDB(): Observable<List<PrepareCryptocurrencyData>> {
        return db.cryptocurrencyDAO().getDataList().map {
            // маппинг в отдельном маппере
            val dataList = mutableListOf<PrepareCryptocurrencyData>()
            // Испольузй это при маппинге листа - красивее, удобнее
            // val mappedList = it.map {  }
            it.forEach { data ->
                dataList.add(
                    PrepareCryptocurrencyData(
                        imageReference = data.imageReference,
                        cryptocurrencyName = data.cryptocurrencyName,
                        cryptocurrencyPriceUSD = data.cryptocurrencyPriceUSD
                    )
                )
            }
            return@map dataList
        }
    }

    /*
    нет обработки ошибок. Если что-то пойдёт не так - крашнется вся прилага, если строки не вставятся - ты об этом не узнаешь.
    Подсказка - все операции в БД возвращают какой-то результат, чекни документацю по руму.
    Подсказка 2 - Дальше этого метода этот результат уйти не должен,
    лучше возвращай Compeltable, который может вернуть ошибку в случае чего
     */
    fun insertCurrencyData(currencyData: Data, symbol: String) {
        db.cryptocurrencyDAO().insertData(
            // зачем создавать лист из 1го объекта?
            listOf(
                // маппинг в отдельном маппере
                CryptocurrencyDataDB(
                    imageReference = symbol,
                    cryptocurrencyName = currencyData.name,
                    cryptocurrencyPriceUSD = currencyData.quote.uSD.price,
                    symbol = currencyData.symbol,
                    slug = currencyData.slug,
                    numMarketPairs = currencyData.num_market_pairs,
                    dateAdded = currencyData.date_added
                )
            )
        )
    }
}