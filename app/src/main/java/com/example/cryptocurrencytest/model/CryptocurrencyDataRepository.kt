package com.example.cryptocurrencytest.model

import com.example.cryptocurrencytest.model.cryptocurrencyapi.CryptocurrencyService
import com.example.cryptocurrencytest.model.db.CryptocurrencyDB
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.entity.currency.Data
import com.example.cryptocurrencytest.model.mapper.MapperCryptocurrencyDataDBToPrepareCryptocurrencyData
import com.example.cryptocurrencytest.model.mapper.MapperCurrencyDataToCryptocurrencyDataDB
import io.reactivex.Observable
import io.reactivex.Single

class CryptocurrencyDataRepository(
    private val cryptocurrencyService: CryptocurrencyService,
    private val db: CryptocurrencyDB
) {

    fun updateCryptocurrencyData() = cryptocurrencyService.makeGetCryptocurrencyDataRequest()

    fun getSymbolURL(currencyData: Data): Single<String> {
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

    fun getDataFromDB(): Observable<List<PrepareCryptocurrencyData>> {
        return db.cryptocurrencyDAO().getDataList().map {
            MapperCryptocurrencyDataDBToPrepareCryptocurrencyData().map(it)
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
            MapperCurrencyDataToCryptocurrencyDataDB().map(Pair(currencyData, symbol))
        )
    }
}