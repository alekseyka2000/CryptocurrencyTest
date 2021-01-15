package com.example.cryptocurrencytest.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptocurrencytest.model.entity.CryptocurrencyDataDB
import com.example.cryptocurrencytest.model.entity.PrepareCryptocurrencyData

@Database(entities = [CryptocurrencyDataDB::class], version = 1, exportSchema = true)
abstract class CryptocurrencyDB : RoomDatabase() {
    abstract fun cryptocurrencyDAO(): CryptocurrencyDAO

    companion object{
        @Volatile
        private var INSTANCE: CryptocurrencyDB? = null

        fun getDB(context: Context): CryptocurrencyDB {
            return INSTANCE ?: synchronized(this){
                val instance = (Room.databaseBuilder(
                    context.applicationContext,
                    CryptocurrencyDB::class.java,
                    "cryptocurrency_database"
                ).build() )
                INSTANCE = instance
                instance
            }
        }
    }
}